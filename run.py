from flask import Flask, request, jsonify, send_file
import os

from config import UPLOAD_FOLDER, MAX_FILE_SIZE_MEMORY, allowed_extensions
from utils import allowed_file, global_search_by_filename

app = Flask(__name__)

for folder in UPLOAD_FOLDER.values():
    os.makedirs(folder, exist_ok=True)

app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


@app.route('/upload', methods=['POST'])
def upload_file():
    try:
        if 'filename' not in request.files or 'folder' not in request.form:
            raise ValueError('No file or type provided.')

        file = request.files['filename']
        file_type = request.form['folder'].lower()

        file.save(f'/tmp/{file.filename}')
        size = os.stat(f'/tmp/{file.filename}').st_size

        if size > MAX_FILE_SIZE_MEMORY:
            raise ValueError('File too large')

        if file.content_length > MAX_FILE_SIZE_MEMORY:
            raise ValueError('File too large')

        if not allowed_file(file.filename, allowed_extensions[file_type]):
            raise ValueError('File type is not supported.')

        filepath = \
            os.path.join(app.config['UPLOAD_FOLDER'][file_type], file.filename)

        if os.path.exists(filepath):
            raise ValueError('Filename should be unique.')

        file.save(filepath)

        return jsonify({'filename': file.filename, 'folder': filepath}), 200

    except ValueError as e:
        if str(e) == "File type is not supported.":
            return jsonify({'error': str(e)}), 415
        elif str(e) == "File too large":
            return jsonify({'error': str(e)}), 413
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
            raise ValueError('The specified file is not found')

    except ValueError as e:
        if str(e) == "The specified file is not found":
            return jsonify({'error': str(e)}), 404
        return jsonify({'error': str(e)}), 400


@app.route("/download", methods=['GET'])
def download_file():
    try:
        if 'filename' not in request.args:
            raise ValueError('No filename provided.')

        full_filename = request.args['filename']
        _, filename = os.path.split(full_filename)

        filepath = global_search_by_filename(filename)

        if not filepath or not os.path.exists(filepath):
            raise ValueError('The specified file is not found')

        return send_file(filepath, as_attachment=True)

    except ValueError as e:
        if str(e) == "The specified file is not found":
            return jsonify({'error': str(e)}), 404
        return jsonify({'error': str(e)}), 400


if __name__ == '__main__':
    app.run(debug=True, host="0.0.0.0", port=5000)
