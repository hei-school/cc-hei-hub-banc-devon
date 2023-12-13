UPLOAD_FOLDER = {
    'images': 'uploads/images',
    'videos': 'uploads/videos',
    'pdfs': 'uploads/pdfs',
    'docs': 'uploads/docs'
}

MAX_FILE_SIZE_MEMORY = 21 * 1024 * 1024

allowed_extensions = {
    'images': {'jpg', 'jpeg', 'png'},
    'videos': {'mp4', 'avi', 'mov', 'mkv'},
    'pdfs': {'pdf'},
    'docs': {'docx'}
}
