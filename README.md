

# Evaluación Técnica

### Instructivo
                    
####Pre requisitos

Usé Gradle como herramienta de automatización de compilación y empaquetado del código.

	Gradle 7.5.1
	JDK Java sourceCompatibility = '1.11'

He creado este componente para que pueda ser ejecutado sin la necesidad de recrear previamente un esquema de datos.  Usé para este propósito [H2 Database Engine](https://www.h2database.com/), un motor base de datos Java, donde los esquemas de datos pueden ser embebidos y usados en memoria. 

Hibernate quedó configurado para que pueda recrear sus entidades en memoría, a partir de un modelo prestablecido en base los @Entity incluidos en el proyecto


	spring.jpa.hibernate.ddl-auto=create

### Instalación

Nos posicionamos en la carpeta raíz, en la cual se clonó el proyecto, luego:

	>`$ gradle clean`

	>`$ gradle build`

### Arranque 

	>`$ java -jar build/libs/evaluaciontecnica.jar`
	

Posterior al arranque, he incluido un proceso de volcado inicial de datos, básicamente incorporo algunos registros en la base de datos para que puedan ser usados al momento de evaluar la solución.  Se incluyen registros de:

- Tareas
- Personas
- Telefono

### Ejecución

Junto a los entregables de este proyecto, incluyo colección [Postman](https://www.postman.com/) con todos los endpoints disponibles de la API. 
