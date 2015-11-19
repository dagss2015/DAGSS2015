# DAGSS2015

## Pasos previos

### Crear tablas y carga de entidades/usuarios iniciales

* Crear base de datos "dagss" y usuario "dagss/dagss" si no existe
```
$ mysql -u root -p    [pedirá la contraseña de administrador de MySQL]

mysql> create database dagss;
mysql> grant all privileges on dagss.* to dagss@localhost identified by "dagss";
```
* Cargar definición de tablas y usuarios iniciales (desde la raiz del proyecto)
```
$mysql -D dagss -u dagss -p < sql/sql-creacion-tablas.sql      [pedirá la contraseña del usuario 'dagss']    
$mysql -D dagss -u dagss -p < sql/sql-entidades-iniciales.sql  [pedirá la contraseña del usuario 'dagss']

```
