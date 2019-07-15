# Trabajo practico java apis


Uso de la api: 

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /collection/<span style="color:red">**:search**</span>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **searhc:** Search el *nombre* de la coleccion de items que vamos a buscar

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Sobre esta url, se puede hacer:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **get :** para obtener una lista items pertenecientes a la coleccion *search* 

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **post :** para crear/agregar un item pertenecientes a la coleccion *search* 


&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /collection/<span style="color:red">**:search/titles**</span>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **get :** Obtiene la lista *search* pero solo trae los titutlos de los items de esa coleccion

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /collection/<span style="color:red">**:search/:id**</span>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **get :** Para obtener un item con el *:id* dado, en la coleccion *:search*

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **post :** Para editar el item con el *:id* dado, en la coleccion *:search*

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **delete :** Para borrar el item con el *:id* dado, en la coleccion *:search*

*The postman collection:*

https://www.getpostman.com/collections/a0a22d30b5275ba991c7