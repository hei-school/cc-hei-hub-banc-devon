# _HEI CLOUD_

This project allows users to upload, search and download files to the following directories:

```plaintext

└───uploades
    ├───images
    ├───videos
    ├───pdfs
    ├───docs
    
```

### Specification
You can find openapi specification here:

```tree
└───doc
    ├───api.yml
```

To have a preview, you can use extension or open it in [swagger_editor](https://editor.swagger.io/)

## LINTER:
We use google formatter for code style

## FEATURES:

1. **Upload Files:**
    - Upload files to a specific directory.

2. **Search Files:**
    - Search for a file by filename across all directories.

3. **Download File:**
    - Download a specific file.

### Error handling:

```plaintext
     1. 'No file or type provided.': Raised if neither the file nor the type is provided in the request.

     2. 'Filename is invalid.': Raised if the filename field is empty.

     3. 'File too large': Raised if the file size exceeds the defined limit (MAX_FILE_SIZE_MEMORY).

     4. 'Invalid file type, should be: images/videos/pdfs/docs.': Raised if the provided file type is not among the allowed types.

     5. 'File type is not supported.': Raised if the file type is not supported.

     6. 'Filename should be unique.': Raised if a file with the same name already exists in the destination folder.

     7. 'No filename provided.': Raised if no filename is provided when requesting to download a file.

     8. 'File not found': Raised if the specified file is not found during the download attempt.
```

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/wTBA-Etm)
