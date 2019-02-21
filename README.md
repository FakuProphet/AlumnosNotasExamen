# Alumnos Notas Examen

En esta base de datos las tablas Alumno y Curso ya se encuentran previamente cargadas y no se requieren nuevas inserciones. Por ese motivo las claves primarias de dichas tablas no son autonuméricas. Por otro lado, la nota de un parcial es siempre mayor o igual a a 1 y menor o igual a 10, en el caso de los alumnos ausentes se regista nota 0. La tabla de notas tiene clave compuesta por el legajo del alumno y el número de parcial, el cual puede valer 1 o 2 para los parciales y 3 para el recuperatorio. El programa debe ofrecer pantallas para poder realizar lo siguiente:

• Carga de notas: debe permitir insertar la nota que obtuvo cierto alumno. La pantalla correspondiente debe mostrar controles adecuados     para cada dato a ingresar.
• Lista de notas: debe mostrar en una tabla o lista todas las notas, incluyendo el nombre del alumno y el nombre del curso. 
• Reportes: debe mostrar la siguiente información: 
    – Promedio de todas las notas de los presentes. 
    – Turno con peor promedio de notas. 
    – Porcentaje de ausentes sobre el total de los que rindieron
