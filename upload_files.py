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

def allowed_file(filename, allowed_extensions):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in allowed_extensions

@app.route('/upload', methods=['POST'])
def upload_file():
    if 'filename' not in request.files or 'folder' not in request.form:
        return jsonify({'error': 'No file or type provided. '}), 400

    file = request.files['filename']
    file_type = request.form['folder'].lower()

    allowed_extensions = {
        'images': {'jpg', 'jpeg', 'png'},
        'videos': {'mp4', 'avi', 'mov', 'mkv'},
        'pdfs': {'pdf'},
        'docs': {'docx'}
    }

    if file_type not in UPLOAD_FOLDER or not allowed_file(file.filename, allowed_extensions[file_type]):
        return jsonify({'error': 'Invalid file type or extension.'}), 400

    filepath = os.path.join(app.config['UPLOAD_FOLDER'][file_type], file.filename)

    if os.path.exists(filepath):
        return jsonify({'error': 'filename should be unique.'}), 400

    file.save(filepath)

    return jsonify({'success': f'{file_type} uploaded.'}), 200

if __name__ == '__main__':
    app.run(debug=True, host="0.0.0.0", port=5000)
