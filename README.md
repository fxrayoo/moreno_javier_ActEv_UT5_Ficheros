RESUMEN PROYECTO:
He desarrollado una aplicación de consola en Java que permite gestionar usuarios y notas personales siguiendo las instrucciones. Los datos se guardan en ficheros de texto.

-----------------------------------------------------------------------------------------------------------------------------------------------

ESTRUCTURA:

- data/users.txt: Aquí se guardan los emails y contraseñas (email;password).
- data/usuarios: Esta carpeta es donde se crean los directorios de cada usuario.
- Sanitización: Reemplaza los "." y "@" por guiones bajos "_".

-----------------------------------------------------------------------------------------------------------------------------------------------

REQUISITOS:

- Ficheros: Uso de la librería "java.nio.file" (Path y Files).
- Try-with-resources: He usado esta estructura para asegurar que la lectura y escritura se cierran sin problemas
- Validaciones: El programa comprueba que los campos no estén vacíos, que el email tenga un formato básico y que no se registren usuarios duplicados.
- Gestión de Notas: Se pueden crear, listar, ver el contenido de una nota específica y borrarlas. Al borrar, el programa lee el archivo y lo sobreescribe

-----------------------------------------------------------------------------------------------------------------------------------------------

BONUS

- Búsqueda: He añadido una opción para buscar notas por palabra clave.
- Git/GitHub: He subido el proyecto a GitHub con varios commits para mostrar el progreso y he incluido un ".gitignore" para no subir archivos basura de Java.

-----------------------------------------------------------------------------------------------------------------------------------------------

EJECUTAR:
Ejecutamos la clase "App.java". Y Si no existiera la carpeta "data", el programa la creararía automáticamente al registrar al primer usuario.