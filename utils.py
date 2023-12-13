import os

from config import UPLOAD_FOLDER


def global_search_by_filename(filename):
    for folder in UPLOAD_FOLDER.values():
        filepath = os.path.join(folder, filename)
        if os.path.exists(filepath):
            return filepath
    return None


def allowed_file(filename, allowed_extensions):
    return '.' in filename \
        and filename.rsplit('.', 1)[1].lower() in allowed_extensions
