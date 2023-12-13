from flask import Flask, request, jsonify
import os

app = Flask(__name__)

UPLOAD_FOLDER = {
    'images': 'uploads/images',
    'videos': 'uploads/videos',
    'pdfs': 'uploads/pdfs',
    'docs': 'uploads/docs'
}

for folder in UPLOAD_FOLDER.values():
    os.makedirs(folder, exist_ok=True)

app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


def global_search_by_filename(filename):
    for folder in UPLOAD_FOLDER.values():
        filepath = os.path.join(folder, filename)
        if os.path.exists(filepath):
            return filepath
    return None

def allowed_file(filename, allowed_extensions):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in allowed_extensions

@app.route('/upload', methods=['POST'])
def upload_file():
    try:
        if 'filename' not in request.files or 'folder' not in request.form:
            raise ValueError('No file or type provided.')

        file = request.files['filename']
        file_type = request.form['folder'].lower()

        allowed_extensions = {
            'images': {'jpg', 'jpeg', 'png'},
            'videos': {'mp4', 'avi', 'mov', 'mkv'},
            'pdfs': {'pdf'},
            'docs': {'docx'}
        }

        if file_type not in UPLOAD_FOLDER:
            raise ValueError('Invalid file type, should be: images/videos/pdfs/docs.')

        if not allowed_file(file.filename, allowed_extensions[file_type]):
            raise ValueError('File type is not supported.')

        filepath = os.path.join(app.config['UPLOAD_FOLDER'][file_type], file.filename)

        if os.path.exists(filepath):
            raise ValueError('Filename should be unique.')

        file.save(filepath)

        return jsonify({'filename': f'{file.filename}', 'folder':f'{filepath}'}), 200

    except ValueError as e:
        if str(e) == "File type is not supported.":
            return jsonify({'error': str(e)}), 415
        return jsonify({'error': str(e)}), 400


@app.route('/files', methods=["GET"])
def get_files_by_filename():
    try:
        if 'filename' not in request.args:
            raise ValueError('No filename provided.')

        filename = request.args['filename']
        filepath = global_search_by_filename(filename)

        if filepath:
            return jsonify({'path': filepath}), 200
        else:
            raise ValueError('File not found')

    except ValueError as e:
        if str(e) == "File not found":
            return jsonify({'error': str(e)}), 404
        return jsonify({'error': str(e)}), 400

if __name__ == '__main__':
    app.run(debug=True, host="0.0.0.0", port=5000)
