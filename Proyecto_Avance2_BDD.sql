---Tablas
CREATE TABLE Provincias (
    ID_Provincia NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Nombre_Provincia VARCHAR2(100) NOT NULL
);

CREATE TABLE Cantones (
    ID_Canton NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Nombre_Canton VARCHAR2(100) NOT NULL,
    ID_Provincia NUMBER NOT NULL,
    FOREIGN KEY (ID_Provincia) REFERENCES Provincias(ID_Provincia)
);

CREATE TABLE Distritos (
    ID_Distrito NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Nombre_Distrito VARCHAR2(100) NOT NULL,
    ID_Canton NUMBER NOT NULL,
    FOREIGN KEY (ID_Canton) REFERENCES Cantones(ID_Canton)
);

CREATE TABLE Direcciones (
    ID_Direccion NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Direccion_Exacta VARCHAR2(250),
    ID_Distrito NUMBER NOT NULL,
    FOREIGN KEY (ID_Distrito) REFERENCES Distritos(ID_Distrito)
);

CREATE TABLE Hoteles (
    ID_Hotel NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Nombre_Hotel VARCHAR2(150) NOT NULL,
    ID_Direccion NUMBER NOT NULL,
    FOREIGN KEY (ID_Direccion) REFERENCES Direcciones(ID_Direccion)
);

CREATE TABLE Hoteles_Telefonos (
    ID_Hotel_Telefono NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ID_Hotel NUMBER NOT NULL,
    Telefono_Hotel VARCHAR2(20),
    FOREIGN KEY (ID_Hotel) REFERENCES Hoteles(ID_Hotel)
);

CREATE TABLE Hoteles_Correos (
    ID_Hotel_Correo NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ID_Hotel NUMBER NOT NULL,
    Correo_Hotel VARCHAR2(100),
    FOREIGN KEY (ID_Hotel) REFERENCES Hoteles(ID_Hotel)
);

CREATE TABLE Clientes (
    Cedula NUMBER PRIMARY KEY,
    Nombre_Cliente VARCHAR2(100) NOT NULL,
    Primer_Apellido VARCHAR2(100) NOT NULL,
    Segundo_Apellido VARCHAR2(100)
);

CREATE TABLE Clientes_Telefonos (
    ID_Cliente_Telefono NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Cedula NUMBER NOT NULL,
    Telefono_Cliente VARCHAR2(20),
    FOREIGN KEY (Cedula) REFERENCES Clientes(Cedula)
);

CREATE TABLE Clientes_Correos (
    ID_Cliente_Correo NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Cedula NUMBER NOT NULL,
    Correo_Cliente VARCHAR2(100),
    FOREIGN KEY (Cedula) REFERENCES Clientes(Cedula)
);

CREATE TABLE Reservaciones (
    ID_Reserva NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Fecha_Entrada DATE NOT NULL,
    Fecha_Salida DATE NOT NULL,
    Cedula NUMBER NOT NULL,
    FOREIGN KEY (Cedula) REFERENCES Clientes(Cedula)
);

CREATE TABLE Check_in_out (
    ID_Check NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Hora_Entrada DATE NOT NULL,
    Hora_Salida DATE,
    Fecha_Entrada DATE NOT NULL,
    Fecha_Salida DATE,
    ID_Reserva NUMBER NOT NULL,
    FOREIGN KEY (ID_Reserva) REFERENCES Reservaciones(ID_Reserva)
);

CREATE TABLE Check_in_out_Devoluciones (
    ID_Devolucion NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ID_Check NUMBER NOT NULL,
    Devolucion VARCHAR2(200),
    FOREIGN KEY (ID_Check) REFERENCES Check_in_out(ID_Check)
);

CREATE TABLE Tipos_Habitaciones (
    ID_Tipo_Habitacion NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Nombre_Tipo_Habitacion VARCHAR2(100) NOT NULL,
    Descripcion_Tipo_Habitacion VARCHAR2(250),
    Precio_Tipo_Habitacion NUMBER(10,2)
);

CREATE TABLE Habitaciones (
    ID_Habitacion NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Numero_Habitacion VARCHAR2(10) NOT NULL,
    Estado_Habitacion VARCHAR2(50),
    ID_Tipo_Habitacion NUMBER NOT NULL,
    ID_Reserva NUMBER,
    ID_Hotel NUMBER NOT NULL,
    FOREIGN KEY (ID_Tipo_Habitacion) REFERENCES Tipos_Habitaciones(ID_Tipo_Habitacion),
    FOREIGN KEY (ID_Hotel) REFERENCES Hoteles(ID_Hotel),
    FOREIGN KEY (ID_Reserva) REFERENCES Reservaciones(ID_Reserva)
);

CREATE TABLE Servicios (
    ID_Servicio NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ID_Hotel NUMBER NOT NULL,
    Nombre_Servicio VARCHAR2(100),
    Descripcion_Servicio VARCHAR2(250),
    Costo_Servicio NUMBER(10,2),
    FOREIGN KEY (ID_Hotel) REFERENCES Hoteles(ID_Hotel)
);

CREATE TABLE Servicios_Reservaciones (
    ID_Servicio NUMBER,
    ID_Reserva NUMBER,
    Fecha DATE,
    PRIMARY KEY (ID_Servicio, ID_Reserva),
    FOREIGN KEY (ID_Servicio) REFERENCES Servicios(ID_Servicio),
    FOREIGN KEY (ID_Reserva) REFERENCES Reservaciones(ID_Reserva)
);

CREATE TABLE Servicios_Habitaciones (
    ID_Servicio_Habitacion NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ID_Tipo_Habitacion NUMBER NOT NULL,
    Nombre_Servicio_Habitacion VARCHAR2(100),
    Descripcion_Servicio_Habitacion VARCHAR2(200),
    FOREIGN KEY (ID_Tipo_Habitacion) REFERENCES Tipos_Habitaciones(ID_Tipo_Habitacion)
);

CREATE TABLE ServiciosHabitaciones_TiposHabitacion (
    ID_Servicio_Habitacion NUMBER NOT NULL,
    ID_Tipo_Habitacion NUMBER NOT NULL,
    PRIMARY KEY (ID_Servicio_Habitacion, ID_Tipo_Habitacion),
    FOREIGN KEY (ID_Servicio_Habitacion) REFERENCES Servicios_Habitaciones(ID_Servicio_Habitacion),
    FOREIGN KEY (ID_Tipo_Habitacion) REFERENCES Tipos_Habitaciones(ID_Tipo_Habitacion)
);

CREATE TABLE Actividades (
    ID_Actividad NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ID_Hotel NUMBER NOT NULL,
    Nombre_Actividad VARCHAR2(100) NOT NULL,
    Descripcion_Actividad VARCHAR2(200),
    Capacidad_Actividad NUMBER,
    FOREIGN KEY (ID_Hotel) REFERENCES Hoteles(ID_Hotel)
);

CREATE TABLE Roles (
    ID_Rol NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Nombre_Rol VARCHAR2(100) NOT NULL,
    Descripcion_Rol VARCHAR2(200)
);

CREATE TABLE Empleados (
    ID_Empleado NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ID_Hotel NUMBER NOT NULL,
    ID_Rol NUMBER NOT NULL,
    Nombre VARCHAR2(100) NOT NULL,
    Apellido1 VARCHAR2(100) NOT NULL,
    Apellido2 VARCHAR2(100),
    FOREIGN KEY (ID_Hotel) REFERENCES Hoteles(ID_Hotel),
    FOREIGN KEY (ID_Rol) REFERENCES Roles(ID_Rol)
);

CREATE TABLE Empleados_Telefonos (
    ID_Empleado_Telefono NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ID_Empleado NUMBER NOT NULL,
    Telefono_Empleado VARCHAR2(20),
    FOREIGN KEY (ID_Empleado) REFERENCES Empleados(ID_Empleado)
);

CREATE TABLE Empleados_Correos (
    ID_Empleado_Correo NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ID_Empleado NUMBER NOT NULL,
    Correo_Empleado VARCHAR2(100),
    FOREIGN KEY (ID_Empleado) REFERENCES Empleados(ID_Empleado)
);

CREATE TABLE Facturas (
    ID_Factura NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ID_Reserva NUMBER NOT NULL,
    Fecha DATE NOT NULL,
    FOREIGN KEY (ID_Reserva) REFERENCES Reservaciones(ID_Reserva)
);

CREATE TABLE Detalle_Factura (
    ID_DetalleFactura NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ID_Factura NUMBER NOT NULL,
    ID_Tipo_Habitacion NUMBER NOT NULL,
    ID_Servicios NUMBER NOT NULL,
    Monto_Total NUMBER(10,2),
    FOREIGN KEY (ID_Factura) REFERENCES Facturas(ID_Factura),
    FOREIGN KEY (ID_Tipo_Habitacion) REFERENCES Tipos_Habitaciones(ID_Tipo_Habitacion),
    FOREIGN KEY (ID_Servicios) REFERENCES Servicios(ID_Servicio)
);
/

--- Paquetes con procedimientos almacenados del CRUD 
-- Provincias 
CREATE OR REPLACE PACKAGE pkg_provincias AS
    PROCEDURE sp_insert_provincia(
        p_nombre IN Provincias.Nombre_Provincia%TYPE,
        p_mensaje OUT VARCHAR2
    );

    PROCEDURE sp_update_provincia(
        p_id IN Provincias.ID_Provincia%TYPE,
        p_nombre IN Provincias.Nombre_Provincia%TYPE,
        p_mensaje OUT VARCHAR2
    );

    PROCEDURE sp_delete_provincia(
        p_id IN Provincias.ID_Provincia%TYPE,
        p_mensaje OUT VARCHAR2
    );
END pkg_provincias;

CREATE OR REPLACE PACKAGE BODY pkg_provincias AS

    PROCEDURE sp_insert_provincia(
        p_nombre IN Provincias.Nombre_Provincia%TYPE,
        p_mensaje OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Provincias(Nombre_Provincia)
        VALUES (p_nombre);

        p_mensaje := 'Provincia insertada correctamente';
    END;

    PROCEDURE sp_update_provincia(
        p_id IN Provincias.ID_Provincia%TYPE,
        p_nombre IN Provincias.Nombre_Provincia%TYPE,
        p_mensaje OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Provincias
        SET Nombre_Provincia = p_nombre
        WHERE ID_Provincia = p_id;

        p_mensaje := 'Provincia actualizada correctamente';
    END;

    PROCEDURE sp_delete_provincia(
        p_id IN Provincias.ID_Provincia%TYPE,
        p_mensaje OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Provincias WHERE ID_Provincia = p_id;

        p_mensaje := 'Provincia eliminada correctamente';
    END;

END pkg_provincias;

--- Cantones 
CREATE OR REPLACE PACKAGE pkg_cantones AS
    PROCEDURE sp_insert_canton(
        p_nombre IN Cantones.Nombre_Canton%TYPE,
        p_id_provincia IN Cantones.ID_Provincia%TYPE,
        p_mensaje OUT VARCHAR2
    );

    PROCEDURE sp_update_canton(
        p_id IN Cantones.ID_Canton%TYPE,
        p_nombre IN Cantones.Nombre_Canton%TYPE,
        p_id_provincia IN Cantones.ID_Provincia%TYPE,
        p_mensaje OUT VARCHAR2
    );

    PROCEDURE sp_delete_canton(
        p_id IN Cantones.ID_Canton%TYPE,
        p_mensaje OUT VARCHAR2
    );
END pkg_cantones;


CREATE OR REPLACE PACKAGE BODY pkg_cantones AS

    PROCEDURE sp_insert_canton(
        p_nombre IN Cantones.Nombre_Canton%TYPE,
        p_id_provincia IN Cantones.ID_Provincia%TYPE,
        p_mensaje OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Cantones(Nombre_Canton, ID_Provincia)
        VALUES (p_nombre, p_id_provincia);
        p_mensaje := 'Cantón insertado correctamente';
    END;

    PROCEDURE sp_update_canton(
        p_id IN Cantones.ID_Canton%TYPE,
        p_nombre IN Cantones.Nombre_Canton%TYPE,
        p_id_provincia IN Cantones.ID_Provincia%TYPE,
        p_mensaje OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Cantones
        SET Nombre_Canton = p_nombre,
            ID_Provincia = p_id_provincia
        WHERE ID_Canton = p_id;

        p_mensaje := 'Cantón actualizado correctamente';
    END;

    PROCEDURE sp_delete_canton(
        p_id IN Cantones.ID_Canton%TYPE,
        p_mensaje OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Cantones WHERE ID_Canton = p_id;
        p_mensaje := 'Cantón eliminado correctamente';
    END;

END pkg_cantones;

--- Distritos 
CREATE OR REPLACE PACKAGE pkg_distritos AS
    PROCEDURE sp_insert_distrito(
        p_nombre IN Distritos.Nombre_Distrito%TYPE,
        p_id_canton IN Distritos.ID_Canton%TYPE,
        p_mensaje OUT VARCHAR2
    );

    PROCEDURE sp_update_distrito(
        p_id IN Distritos.ID_Distrito%TYPE,
        p_nombre IN Distritos.Nombre_Distrito%TYPE,
        p_id_canton IN Distritos.ID_Canton%TYPE,
        p_mensaje OUT VARCHAR2
    );

    PROCEDURE sp_delete_distrito(
        p_id IN Distritos.ID_Distrito%TYPE,
        p_mensaje OUT VARCHAR2
    );
END pkg_distritos;

CREATE OR REPLACE PACKAGE BODY pkg_distritos AS

    PROCEDURE sp_insert_distrito(
        p_nombre IN Distritos.Nombre_Distrito%TYPE,
        p_id_canton IN Distritos.ID_Canton%TYPE,
        p_mensaje OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Distritos(Nombre_Distrito, ID_Canton)
        VALUES (p_nombre, p_id_canton);
        p_mensaje := 'Distrito insertado correctamente';
    END;

    PROCEDURE sp_update_distrito(
        p_id IN Distritos.ID_Distrito%TYPE,
        p_nombre IN Distritos.Nombre_Distrito%TYPE,
        p_id_canton IN Distritos.ID_Canton%TYPE,
        p_mensaje OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Distritos
        SET Nombre_Distrito = p_nombre,
            ID_Canton = p_id_canton
        WHERE ID_Distrito = p_id;

        p_mensaje := 'Distrito actualizado correctamente';
    END;

    PROCEDURE sp_delete_distrito(
        p_id IN Distritos.ID_Distrito%TYPE,
        p_mensaje OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Distritos WHERE ID_Distrito = p_id;
        p_mensaje := 'Distrito eliminado correctamente';
    END;

END pkg_distritos;

--- Direcciones
CREATE OR REPLACE PACKAGE pkg_direcciones AS

    PROCEDURE sp_insert_direccion(
        p_direccion_exacta IN Direcciones.Direccion_Exacta%TYPE,
        p_id_distrito      IN Direcciones.ID_Distrito%TYPE,
        p_mensaje          OUT VARCHAR2
    );

    PROCEDURE sp_update_direccion(
        p_id               IN Direcciones.ID_Direccion%TYPE,
        p_direccion_exacta IN Direcciones.Direccion_Exacta%TYPE,
        p_id_distrito      IN Direcciones.ID_Distrito%TYPE,
        p_mensaje          OUT VARCHAR2
    );

    PROCEDURE sp_delete_direccion(
        p_id      IN Direcciones.ID_Direccion%TYPE,
        p_mensaje OUT VARCHAR2
    );

END pkg_direcciones;

CREATE OR REPLACE PACKAGE BODY pkg_direcciones AS

    PROCEDURE sp_insert_direccion(
        p_direccion_exacta IN Direcciones.Direccion_Exacta%TYPE,
        p_id_distrito      IN Direcciones.ID_Distrito%TYPE,
        p_mensaje          OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Direcciones(Direccion_Exacta, ID_Distrito)
        VALUES (p_direccion_exacta, p_id_distrito);

        p_mensaje := 'Dirección insertada correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            p_mensaje := 'Error al insertar dirección: ' || SQLERRM;
    END;

    PROCEDURE sp_update_direccion(
        p_id               IN Direcciones.ID_Direccion%TYPE,
        p_direccion_exacta IN Direcciones.Direccion_Exacta%TYPE,
        p_id_distrito      IN Direcciones.ID_Distrito%TYPE,
        p_mensaje          OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Direcciones
        SET Direccion_Exacta = p_direccion_exacta,
            ID_Distrito = p_id_distrito
        WHERE ID_Direccion = p_id;

        p_mensaje := 'Dirección actualizada correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            p_mensaje := 'Error al actualizar dirección: ' || SQLERRM;
    END;

    PROCEDURE sp_delete_direccion(
        p_id      IN Direcciones.ID_Direccion%TYPE,
        p_mensaje OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Direcciones WHERE ID_Direccion = p_id;
        p_mensaje := 'Dirección eliminada correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            p_mensaje := 'Error al eliminar dirección: ' || SQLERRM;
    END;

END pkg_direcciones;

--- Hoteles 
CREATE OR REPLACE PACKAGE pkg_hoteles AS

    PROCEDURE sp_insert_hotel(
        p_nombre      IN Hoteles.Nombre_Hotel%TYPE,
        p_id_direccion IN Hoteles.ID_Direccion%TYPE,
        p_mensaje     OUT VARCHAR2
    );

    PROCEDURE sp_update_hotel(
        p_id           IN Hoteles.ID_Hotel%TYPE,
        p_nombre       IN Hoteles.Nombre_Hotel%TYPE,
        p_id_direccion IN Hoteles.ID_Direccion%TYPE,
        p_mensaje      OUT VARCHAR2
    );

    PROCEDURE sp_delete_hotel(
        p_id      IN Hoteles.ID_Hotel%TYPE,
        p_mensaje OUT VARCHAR2
    );

END pkg_hoteles;

CREATE OR REPLACE PACKAGE BODY pkg_hoteles AS

    PROCEDURE sp_insert_hotel(
        p_nombre      IN Hoteles.Nombre_Hotel%TYPE,
        p_id_direccion IN Hoteles.ID_Direccion%TYPE,
        p_mensaje     OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Hoteles(Nombre_Hotel, ID_Direccion)
        VALUES (p_nombre, p_id_direccion);

        p_mensaje := 'Hotel insertado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            p_mensaje := 'Error al insertar hotel: ' || SQLERRM;
    END;

    PROCEDURE sp_update_hotel(
        p_id           IN Hoteles.ID_Hotel%TYPE,
        p_nombre       IN Hoteles.Nombre_Hotel%TYPE,
        p_id_direccion IN Hoteles.ID_Direccion%TYPE,
        p_mensaje      OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Hoteles
        SET Nombre_Hotel = p_nombre,
            ID_Direccion = p_id_direccion
        WHERE ID_Hotel = p_id;

        p_mensaje := 'Hotel actualizado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            p_mensaje := 'Error al actualizar hotel: ' || SQLERRM;
    END;

    PROCEDURE sp_delete_hotel(
        p_id      IN Hoteles.ID_Hotel%TYPE,
        p_mensaje OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Hoteles WHERE ID_Hotel = p_id;
        p_mensaje := 'Hotel eliminado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            p_mensaje := 'Error al eliminar hotel: ' || SQLERRM;
    END;

END pkg_hoteles;

--- Hoteles telefonos 
CREATE OR REPLACE PACKAGE pkg_hoteles_telefonos AS
    
    PROCEDURE sp_insert_hotel_telefono(
        p_id_hotel     IN Hoteles_Telefonos.ID_Hotel%TYPE,
        p_telefono     IN Hoteles_Telefonos.Telefono_Hotel%TYPE,
        p_mensaje      OUT VARCHAR2
    );

    PROCEDURE sp_update_hotel_telefono(
        p_id_hotel_tel IN Hoteles_Telefonos.ID_Hotel_Telefono%TYPE,
        p_id_hotel     IN Hoteles_Telefonos.ID_Hotel%TYPE,
        p_telefono     IN Hoteles_Telefonos.Telefono_Hotel%TYPE,
        p_mensaje      OUT VARCHAR2
    );

    PROCEDURE sp_delete_hotel_telefono(
        p_id_hotel_tel IN Hoteles_Telefonos.ID_Hotel_Telefono%TYPE,
        p_mensaje      OUT VARCHAR2
    );

END pkg_hoteles_telefonos;

CREATE OR REPLACE PACKAGE BODY pkg_hoteles_telefonos AS

    PROCEDURE sp_insert_hotel_telefono(
        p_id_hotel IN Hoteles_Telefonos.ID_Hotel%TYPE,
        p_telefono IN Hoteles_Telefonos.Telefono_Hotel%TYPE,
        p_mensaje  OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Hoteles_Telefonos(ID_Hotel, Telefono_Hotel)
        VALUES (p_id_hotel, p_telefono);

        p_mensaje := 'Teléfono del hotel insertado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            p_mensaje := 'Error al insertar teléfono: ' || SQLERRM;
    END;


    PROCEDURE sp_update_hotel_telefono(
        p_id_hotel_tel IN Hoteles_Telefonos.ID_Hotel_Telefono%TYPE,
        p_id_hotel     IN Hoteles_Telefonos.ID_Hotel%TYPE,
        p_telefono     IN Hoteles_Telefonos.Telefono_Hotel%TYPE,
        p_mensaje      OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Hoteles_Telefonos
        SET ID_Hotel = p_id_hotel,
            Telefono_Hotel = p_telefono
        WHERE ID_Hotel_Telefono = p_id_hotel_tel;

        p_mensaje := 'Teléfono de hotel actualizado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            p_mensaje := 'Error en actualización: ' || SQLERRM;
    END;


    PROCEDURE sp_delete_hotel_telefono(
        p_id_hotel_tel IN Hoteles_Telefonos.ID_Hotel_Telefono%TYPE,
        p_mensaje      OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Hoteles_Telefonos
        WHERE ID_Hotel_Telefono = p_id_hotel_tel;

        p_mensaje := 'Teléfono eliminado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            p_mensaje := 'Error al eliminar teléfono: ' || SQLERRM;
    END;

END pkg_hoteles_telefonos;

--- Hoteles correos
CREATE OR REPLACE PACKAGE pkg_hoteles_correos AS
    
    PROCEDURE sp_insert_hotel_correo(
        p_id_hotel IN Hoteles_Correos.ID_Hotel%TYPE,
        p_correo   IN Hoteles_Correos.Correo_Hotel%TYPE,
        p_mensaje  OUT VARCHAR2
    );

    PROCEDURE sp_update_hotel_correo(
        p_id_correo IN Hoteles_Correos.ID_Hotel_Correo%TYPE,
        p_id_hotel  IN Hoteles_Correos.ID_Hotel%TYPE,
        p_correo    IN Hoteles_Correos.Correo_Hotel%TYPE,
        p_mensaje   OUT VARCHAR2
    );

    PROCEDURE sp_delete_hotel_correo(
        p_id_correo IN Hoteles_Correos.ID_Hotel_Correo%TYPE,
        p_mensaje   OUT VARCHAR2
    );

END pkg_hoteles_correos;

CREATE OR REPLACE PACKAGE BODY pkg_hoteles_correos AS

    PROCEDURE sp_insert_hotel_correo(
        p_id_hotel IN Hoteles_Correos.ID_Hotel%TYPE,
        p_correo   IN Hoteles_Correos.Correo_Hotel%TYPE,
        p_mensaje  OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Hoteles_Correos(ID_Hotel, Correo_Hotel)
        VALUES (p_id_hotel, p_correo);

        p_mensaje := 'Correo del hotel insertado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            p_mensaje := 'Error al insertar correo: ' || SQLERRM;
    END;


    PROCEDURE sp_update_hotel_correo(
        p_id_correo IN Hoteles_Correos.ID_Hotel_Correo%TYPE,
        p_id_hotel  IN Hoteles_Correos.ID_Hotel%TYPE,
        p_correo    IN Hoteles_Correos.Correo_Hotel%TYPE,
        p_mensaje   OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Hoteles_Correos
        SET ID_Hotel     = p_id_hotel,
            Correo_Hotel = p_correo
        WHERE ID_Hotel_Correo = p_id_correo;

        p_mensaje := 'Correo actualizado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            p_mensaje := 'Error en actualización: ' || SQLERRM;
    END;


    PROCEDURE sp_delete_hotel_correo(
        p_id_correo IN Hoteles_Correos.ID_Hotel_Correo%TYPE,
        p_mensaje   OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Hoteles_Correos
        WHERE ID_Hotel_Correo = p_id_correo;

        p_mensaje := 'Correo eliminado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            p_mensaje := 'Error al eliminar correo: ' || SQLERRM;
    END;

END pkg_hoteles_correos;

--- Clientes
CREATE OR REPLACE PACKAGE pkg_clientes AS
    
    PROCEDURE sp_insert_cliente(
        p_cedula          IN Clientes.Cedula%TYPE,
        p_nombre          IN Clientes.Nombre_Cliente%TYPE,
        p_apellido1       IN Clientes.Primer_Apellido%TYPE,
        p_apellido2       IN Clientes.Segundo_Apellido%TYPE,
        p_mensaje         OUT VARCHAR2
    );

    PROCEDURE sp_update_cliente(
        p_cedula          IN Clientes.Cedula%TYPE,
        p_nombre          IN Clientes.Nombre_Cliente%TYPE,
        p_apellido1       IN Clientes.Primer_Apellido%TYPE,
        p_apellido2       IN Clientes.Segundo_Apellido%TYPE,
        p_mensaje         OUT VARCHAR2
    );

    PROCEDURE sp_delete_cliente(
        p_cedula  IN Clientes.Cedula%TYPE,
        p_mensaje OUT VARCHAR2
    );

END pkg_clientes;

CREATE OR REPLACE PACKAGE BODY pkg_clientes AS

    PROCEDURE sp_insert_cliente(
        p_cedula          IN Clientes.Cedula%TYPE,
        p_nombre          IN Clientes.Nombre_Cliente%TYPE,
        p_apellido1       IN Clientes.Primer_Apellido%TYPE,
        p_apellido2       IN Clientes.Segundo_Apellido%TYPE,
        p_mensaje         OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Clientes(Cedula, Nombre_Cliente, Primer_Apellido, Segundo_Apellido)
        VALUES (p_cedula, p_nombre, p_apellido1, p_apellido2);

        p_mensaje := 'Cliente insertado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            p_mensaje := 'Error al insertar cliente: ' || SQLERRM;
    END;

    PROCEDURE sp_update_cliente(
        p_cedula          IN Clientes.Cedula%TYPE,
        p_nombre          IN Clientes.Nombre_Cliente%TYPE,
        p_apellido1       IN Clientes.Primer_Apellido%TYPE,
        p_apellido2       IN Clientes.Segundo_Apellido%TYPE,
        p_mensaje         OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Clientes
        SET Nombre_Cliente = p_nombre,
            Primer_Apellido = p_apellido1,
            Segundo_Apellido = p_apellido2
        WHERE Cedula = p_cedula;

        p_mensaje := 'Cliente actualizado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            p_mensaje := 'Error al actualizar cliente: ' || SQLERRM;
    END;

    PROCEDURE sp_delete_cliente(
        p_cedula  IN Clientes.Cedula%TYPE,
        p_mensaje OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Clientes WHERE Cedula = p_cedula;
        p_mensaje := 'Cliente eliminado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            p_mensaje := 'Error al eliminar cliente: ' || SQLERRM;
    END;

END pkg_clientes;

--- Clientes telefonos 
CREATE OR REPLACE PACKAGE pkg_clientes_telefonos AS

    PROCEDURE sp_insert_cliente_telefono(
        p_cedula     IN Clientes_Telefonos.Cedula%TYPE,
        p_telefono   IN Clientes_Telefonos.Telefono_Cliente%TYPE,
        p_mensaje    OUT VARCHAR2
    );

    PROCEDURE sp_update_cliente_telefono(
        p_id         IN Clientes_Telefonos.ID_Cliente_Telefono%TYPE,
        p_cedula     IN Clientes_Telefonos.Cedula%TYPE,
        p_telefono   IN Clientes_Telefonos.Telefono_Cliente%TYPE,
        p_mensaje    OUT VARCHAR2
    );

    PROCEDURE sp_delete_cliente_telefono(
        p_id      IN Clientes_Telefonos.ID_Cliente_Telefono%TYPE,
        p_mensaje OUT VARCHAR2
    );

END pkg_clientes_telefonos;

CREATE OR REPLACE PACKAGE BODY pkg_clientes_telefonos AS

    PROCEDURE sp_insert_cliente_telefono(
        p_cedula     IN Clientes_Telefonos.Cedula%TYPE,
        p_telefono   IN Clientes_Telefonos.Telefono_Cliente%TYPE,
        p_mensaje    OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Clientes_Telefonos(Cedula, Telefono_Cliente)
        VALUES (p_cedula, p_telefono);

        p_mensaje := 'Teléfono de cliente insertado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            p_mensaje := 'Error al insertar teléfono: ' || SQLERRM;
    END;

    PROCEDURE sp_update_cliente_telefono(
        p_id         IN Clientes_Telefonos.ID_Cliente_Telefono%TYPE,
        p_cedula     IN Clientes_Telefonos.Cedula%TYPE,
        p_telefono   IN Clientes_Telefonos.Telefono_Cliente%TYPE,
        p_mensaje    OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Clientes_Telefonos
        SET Cedula = p_cedula,
            Telefono_Cliente = p_telefono
        WHERE ID_Cliente_Telefono = p_id;

        p_mensaje := 'Teléfono de cliente actualizado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            p_mensaje := 'Error al actualizar teléfono: ' || SQLERRM;
    END;

    PROCEDURE sp_delete_cliente_telefono(
        p_id      IN Clientes_Telefonos.ID_Cliente_Telefono%TYPE,
        p_mensaje OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Clientes_Telefonos WHERE ID_Cliente_Telefono = p_id;

        p_mensaje := 'Teléfono de cliente eliminado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            p_mensaje := 'Error al eliminar teléfono: ' || SQLERRM;
    END;

END pkg_clientes_telefonos;

--- Clientes correos 
CREATE OR REPLACE PACKAGE pkg_clientes_correos AS

    PROCEDURE sp_insert_cliente_correo(
        p_cedula   IN Clientes_Correos.Cedula%TYPE,
        p_correo   IN Clientes_Correos.Correo_Cliente%TYPE,
        p_mensaje  OUT VARCHAR2
    );

    PROCEDURE sp_update_cliente_correo(
        p_id       IN Clientes_Correos.ID_Cliente_Correo%TYPE,
        p_cedula   IN Clientes_Correos.Cedula%TYPE,
        p_correo   IN Clientes_Correos.Correo_Cliente%TYPE,
        p_mensaje  OUT VARCHAR2
    );

    PROCEDURE sp_delete_cliente_correo(
        p_id      IN Clientes_Correos.ID_Cliente_Correo%TYPE,
        p_mensaje OUT VARCHAR2
    );

END pkg_clientes_correos;

CREATE OR REPLACE PACKAGE BODY pkg_clientes_correos AS

    PROCEDURE sp_insert_cliente_correo(
        p_cedula   IN Clientes_Correos.Cedula%TYPE,
        p_correo   IN Clientes_Correos.Correo_Cliente%TYPE,
        p_mensaje  OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Clientes_Correos(Cedula, Correo_Cliente)
        VALUES (p_cedula, p_correo);

        p_mensaje := 'Correo de cliente insertado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            p_mensaje := 'Error al insertar correo: ' || SQLERRM;
    END;

    PROCEDURE sp_update_cliente_correo(
        p_id       IN Clientes_Correos.ID_Cliente_Correo%TYPE,
        p_cedula   IN Clientes_Correos.Cedula%TYPE,
        p_correo   IN Clientes_Correos.Correo_Cliente%TYPE,
        p_mensaje  OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Clientes_Correos
        SET Cedula = p_cedula,
            Correo_Cliente = p_correo
        WHERE ID_Cliente_Correo = p_id;

        p_mensaje := 'Correo de cliente actualizado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            p_mensaje := 'Error al actualizar correo: ' || SQLERRM;
    END;

    PROCEDURE sp_delete_cliente_correo(
        p_id      IN Clientes_Correos.ID_Cliente_Correo%TYPE,
        p_mensaje OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Clientes_Correos WHERE ID_Cliente_Correo = p_id;

        p_mensaje := 'Correo de cliente eliminado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            p_mensaje := 'Error al eliminar correo: ' || SQLERRM;
    END;

END pkg_clientes_correos;

--- Reservaciones
CREATE OR REPLACE PACKAGE PKG_RESERVACIONES AS
    PROCEDURE SP_INSERT_RESERVA(
        P_FECHA_ENTRADA  IN DATE,
        P_FECHA_SALIDA   IN DATE,
        P_CEDULA         IN NUMBER,
        P_MENSAJE        OUT VARCHAR2
    );

    PROCEDURE SP_UPDATE_RESERVA(
        P_ID_RESERVA     IN NUMBER,
        P_FECHA_ENTRADA  IN DATE,
        P_FECHA_SALIDA   IN DATE,
        P_CEDULA         IN NUMBER,
        P_MENSAJE        OUT VARCHAR2
    );

    PROCEDURE SP_DELETE_RESERVA(
        P_ID_RESERVA     IN NUMBER,
        P_MENSAJE        OUT VARCHAR2
    );
END PKG_RESERVACIONES;

CREATE OR REPLACE PACKAGE BODY PKG_RESERVACIONES AS

    PROCEDURE SP_INSERT_RESERVA(
        P_FECHA_ENTRADA IN DATE,
        P_FECHA_SALIDA  IN DATE,
        P_CEDULA        IN NUMBER,
        P_MENSAJE       OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Reservaciones(Fecha_Entrada, Fecha_Salida, Cedula)
        VALUES (P_FECHA_ENTRADA, P_FECHA_SALIDA, P_CEDULA);

        P_MENSAJE := 'Reserva registrada correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al registrar reserva: ' || SQLERRM;
    END SP_INSERT_RESERVA;


    PROCEDURE SP_UPDATE_RESERVA(
        P_ID_RESERVA    IN NUMBER,
        P_FECHA_ENTRADA IN DATE,
        P_FECHA_SALIDA  IN DATE,
        P_CEDULA        IN NUMBER,
        P_MENSAJE       OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Reservaciones
        SET Fecha_Entrada = P_FECHA_ENTRADA,
            Fecha_Salida  = P_FECHA_SALIDA,
            Cedula        = P_CEDULA
        WHERE ID_Reserva = P_ID_RESERVA;

        P_MENSAJE := 'Reserva actualizada correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al actualizar reserva: ' || SQLERRM;
    END SP_UPDATE_RESERVA;


    PROCEDURE SP_DELETE_RESERVA(
        P_ID_RESERVA IN NUMBER,
        P_MENSAJE    OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Reservaciones
        WHERE ID_Reserva = P_ID_RESERVA;

        P_MENSAJE := 'Reserva eliminada correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al eliminar reserva: ' || SQLERRM;
    END SP_DELETE_RESERVA;

END PKG_RESERVACIONES;

--- Chek in out
CREATE OR REPLACE PACKAGE PKG_CHECK_IN_OUT AS
    PROCEDURE SP_INSERT_CHECK(
        P_HORA_ENTRADA IN DATE,
        P_HORA_SALIDA  IN DATE,
        P_FECHA_ENTRADA IN DATE,
        P_FECHA_SALIDA  IN DATE,
        P_ID_RESERVA   IN NUMBER,
        P_MENSAJE      OUT VARCHAR2
    );

    PROCEDURE SP_UPDATE_CHECK(
        P_ID_CHECK      IN NUMBER,
        P_HORA_ENTRADA  IN DATE,
        P_HORA_SALIDA   IN DATE,
        P_FECHA_ENTRADA IN DATE,
        P_FECHA_SALIDA  IN DATE,
        P_ID_RESERVA    IN NUMBER,
        P_MENSAJE       OUT VARCHAR2
    );

    PROCEDURE SP_DELETE_CHECK(
        P_ID_CHECK IN NUMBER,
        P_MENSAJE  OUT VARCHAR2
    );
END PKG_CHECK_IN_OUT;

CREATE OR REPLACE PACKAGE BODY PKG_CHECK_IN_OUT AS

    PROCEDURE SP_INSERT_CHECK(
        P_HORA_ENTRADA IN DATE,
        P_HORA_SALIDA  IN DATE,
        P_FECHA_ENTRADA IN DATE,
        P_FECHA_SALIDA  IN DATE,
        P_ID_RESERVA   IN NUMBER,
        P_MENSAJE      OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Check_in_out(Hora_Entrada, Hora_Salida, Fecha_Entrada, Fecha_Salida, ID_Reserva)
        VALUES(P_HORA_ENTRADA, P_HORA_SALIDA, P_FECHA_ENTRADA, P_FECHA_SALIDA, P_ID_RESERVA);

        P_MENSAJE := 'Check in/out registrado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al registrar check: ' || SQLERRM;
    END SP_INSERT_CHECK;


    PROCEDURE SP_UPDATE_CHECK(
        P_ID_CHECK     IN NUMBER,
        P_HORA_ENTRADA IN DATE,
        P_HORA_SALIDA  IN DATE,
        P_FECHA_ENTRADA IN DATE,
        P_FECHA_SALIDA  IN DATE,
        P_ID_RESERVA   IN NUMBER,
        P_MENSAJE      OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Check_in_out
        SET Hora_Entrada = P_HORA_ENTRADA,
            Hora_Salida = P_HORA_SALIDA,
            Fecha_Entrada = P_FECHA_ENTRADA,
            Fecha_Salida = P_FECHA_SALIDA,
            ID_Reserva = P_ID_RESERVA
        WHERE ID_Check = P_ID_CHECK;

        P_MENSAJE := 'Check actualizado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al actualizar check: ' || SQLERRM;
    END SP_UPDATE_CHECK;


    PROCEDURE SP_DELETE_CHECK(
        P_ID_CHECK IN NUMBER,
        P_MENSAJE  OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Check_in_out
        WHERE ID_Check = P_ID_CHECK;

        P_MENSAJE := 'Check eliminado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al eliminar check: ' || SQLERRM;
    END SP_DELETE_CHECK;

END PKG_CHECK_IN_OUT;

--- Check devoluciones
CREATE OR REPLACE PACKAGE PKG_CHECK_DEVOLUCIONES AS
    PROCEDURE SP_INSERT_DEVOLUCION(
        P_ID_CHECK   IN NUMBER,
        P_DEVOLUCION IN VARCHAR2,
        P_MENSAJE    OUT VARCHAR2
    );

    PROCEDURE SP_UPDATE_DEVOLUCION(
        P_ID_DEVOLUCION IN NUMBER,
        P_ID_CHECK      IN NUMBER,
        P_DEVOLUCION    IN VARCHAR2,
        P_MENSAJE       OUT VARCHAR2
    );

    PROCEDURE SP_DELETE_DEVOLUCION(
        P_ID_DEVOLUCION IN NUMBER,
        P_MENSAJE       OUT VARCHAR2
    );
END PKG_CHECK_DEVOLUCIONES;

CREATE OR REPLACE PACKAGE BODY PKG_CHECK_DEVOLUCIONES AS

    PROCEDURE SP_INSERT_DEVOLUCION(
        P_ID_CHECK   IN NUMBER,
        P_DEVOLUCION IN VARCHAR2,
        P_MENSAJE    OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Check_in_out_Devoluciones(ID_Check, Devolucion)
        VALUES(P_ID_CHECK, P_DEVOLUCION);

        P_MENSAJE := 'Devolución registrada correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al registrar devolución: ' || SQLERRM;
    END SP_INSERT_DEVOLUCION;


    PROCEDURE SP_UPDATE_DEVOLUCION(
        P_ID_DEVOLUCION IN NUMBER,
        P_ID_CHECK      IN NUMBER,
        P_DEVOLUCION    IN VARCHAR2,
        P_MENSAJE       OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Check_in_out_Devoluciones
        SET ID_Check = P_ID_CHECK,
            Devolucion = P_DEVOLUCION
        WHERE ID_Devolucion = P_ID_DEVOLUCION;

        P_MENSAJE := 'Devolución actualizada correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al actualizar devolución: ' || SQLERRM;
    END SP_UPDATE_DEVOLUCION;


    PROCEDURE SP_DELETE_DEVOLUCION(
        P_ID_DEVOLUCION IN NUMBER,
        P_MENSAJE       OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Check_in_out_Devoluciones
        WHERE ID_Devolucion = P_ID_DEVOLUCION;

        P_MENSAJE := 'Devolución eliminada correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al eliminar devolución: ' || SQLERRM;
    END SP_DELETE_DEVOLUCION;

END PKG_CHECK_DEVOLUCIONES;

--- Tipos de habitaciones
CREATE OR REPLACE PACKAGE PKG_TIPOS_HABITACIONES AS
    PROCEDURE SP_INSERT_TIPO(
        P_NOMBRE     IN VARCHAR2,
        P_DESCRIPCION IN VARCHAR2,
        P_PRECIO      IN NUMBER,
        P_MENSAJE     OUT VARCHAR2
    );

    PROCEDURE SP_UPDATE_TIPO(
        P_ID_TIPO     IN NUMBER,
        P_NOMBRE      IN VARCHAR2,
        P_DESCRIPCION IN VARCHAR2,
        P_PRECIO      IN NUMBER,
        P_MENSAJE     OUT VARCHAR2
    );

    PROCEDURE SP_DELETE_TIPO(
        P_ID_TIPO IN NUMBER,
        P_MENSAJE OUT VARCHAR2
    );
END PKG_TIPOS_HABITACIONES;

CREATE OR REPLACE PACKAGE BODY PKG_TIPOS_HABITACIONES AS

    PROCEDURE SP_INSERT_TIPO(
        P_NOMBRE      IN VARCHAR2,
        P_DESCRIPCION IN VARCHAR2,
        P_PRECIO      IN NUMBER,
        P_MENSAJE     OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Tipos_Habitaciones(
            Nombre_Tipo_Habitacion, Descripcion_Tipo_Habitacion, Precio_Tipo_Habitacion
        ) VALUES (
            P_NOMBRE, P_DESCRIPCION, P_PRECIO
        );

        P_MENSAJE := 'Tipo de habitación registrado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al registrar tipo de habitación: ' || SQLERRM;
    END SP_INSERT_TIPO;


    PROCEDURE SP_UPDATE_TIPO(
        P_ID_TIPO     IN NUMBER,
        P_NOMBRE      IN VARCHAR2,
        P_DESCRIPCION IN VARCHAR2,
        P_PRECIO      IN NUMBER,
        P_MENSAJE     OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Tipos_Habitaciones
        SET Nombre_Tipo_Habitacion = P_NOMBRE,
            Descripcion_Tipo_Habitacion = P_DESCRIPCION,
            Precio_Tipo_Habitacion = P_PRECIO
        WHERE ID_Tipo_Habitacion = P_ID_TIPO;

        P_MENSAJE := 'Tipo de habitación actualizado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al actualizar tipo de habitación: ' || SQLERRM;
    END SP_UPDATE_TIPO;


    PROCEDURE SP_DELETE_TIPO(
        P_ID_TIPO IN NUMBER,
        P_MENSAJE OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Tipos_Habitaciones
        WHERE ID_Tipo_Habitacion = P_ID_TIPO;

        P_MENSAJE := 'Tipo de habitación eliminado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al eliminar tipo de habitación: ' || SQLERRM;
    END SP_DELETE_TIPO;

END PKG_TIPOS_HABITACIONES;

--- Habitaciones 
CREATE OR REPLACE PACKAGE PKG_HABITACIONES AS
    PROCEDURE SP_INSERT_HABITACION(
        P_NUMERO        IN VARCHAR2,
        P_ESTADO        IN VARCHAR2,
        P_ID_TIPO       IN NUMBER,
        P_ID_RESERVA    IN NUMBER,
        P_ID_HOTEL      IN NUMBER,
        P_MENSAJE       OUT VARCHAR2
    );

    PROCEDURE SP_UPDATE_HABITACION(
        P_ID_HABITACION IN NUMBER,
        P_NUMERO        IN VARCHAR2,
        P_ESTADO        IN VARCHAR2,
        P_ID_TIPO       IN NUMBER,
        P_ID_RESERVA    IN NUMBER,
        P_ID_HOTEL      IN NUMBER,
        P_MENSAJE       OUT VARCHAR2
    );

    PROCEDURE SP_DELETE_HABITACION(
        P_ID_HABITACION IN NUMBER,
        P_MENSAJE       OUT VARCHAR2
    );
END PKG_HABITACIONES;

CREATE OR REPLACE PACKAGE BODY PKG_HABITACIONES AS

    PROCEDURE SP_INSERT_HABITACION(
        P_NUMERO     IN VARCHAR2,
        P_ESTADO     IN VARCHAR2,
        P_ID_TIPO    IN NUMBER,
        P_ID_RESERVA IN NUMBER,
        P_ID_HOTEL   IN NUMBER,
        P_MENSAJE    OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Habitaciones(
            Numero_Habitacion, Estado_Habitacion, ID_Tipo_Habitacion,
            ID_Reserva, ID_Hotel
        ) VALUES (
            P_NUMERO, P_ESTADO, P_ID_TIPO, P_ID_RESERVA, P_ID_HOTEL
        );

        P_MENSAJE := 'Habitación registrada correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al registrar habitación: ' || SQLERRM;
    END SP_INSERT_HABITACION;


    PROCEDURE SP_UPDATE_HABITACION(
        P_ID_HABITACION IN NUMBER,
        P_NUMERO        IN VARCHAR2,
        P_ESTADO        IN VARCHAR2,
        P_ID_TIPO       IN NUMBER,
        P_ID_RESERVA    IN NUMBER,
        P_ID_HOTEL      IN NUMBER,
        P_MENSAJE       OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Habitaciones
        SET Numero_Habitacion = P_NUMERO,
            Estado_Habitacion = P_ESTADO,
            ID_Tipo_Habitacion = P_ID_TIPO,
            ID_Reserva = P_ID_RESERVA,
            ID_Hotel = P_ID_HOTEL
        WHERE ID_Habitacion = P_ID_HABITACION;

        P_MENSAJE := 'Habitación actualizada correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al actualizar habitación: ' || SQLERRM;
    END SP_UPDATE_HABITACION;


    PROCEDURE SP_DELETE_HABITACION(
        P_ID_HABITACION IN NUMBER,
        P_MENSAJE       OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Habitaciones
        WHERE ID_Habitacion = P_ID_HABITACION;

        P_MENSAJE := 'Habitación eliminada correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al eliminar habitación: ' || SQLERRM;
    END SP_DELETE_HABITACION;

END PKG_HABITACIONES;

--- Servicios 
CREATE OR REPLACE PACKAGE PKG_SERVICIOS AS
    PROCEDURE SP_INSERT_SERVICIO(
        P_ID_HOTEL     IN NUMBER,
        P_NOMBRE       IN VARCHAR2,
        P_DESCRIPCION  IN VARCHAR2,
        P_COSTO        IN NUMBER,
        P_MENSAJE      OUT VARCHAR2
    );

    PROCEDURE SP_UPDATE_SERVICIO(
        P_ID_SERVICIO  IN NUMBER,
        P_ID_HOTEL     IN NUMBER,
        P_NOMBRE       IN VARCHAR2,
        P_DESCRIPCION  IN VARCHAR2,
        P_COSTO        IN NUMBER,
        P_MENSAJE      OUT VARCHAR2
    );

    PROCEDURE SP_DELETE_SERVICIO(
        P_ID_SERVICIO IN NUMBER,
        P_MENSAJE OUT VARCHAR2
    );
END PKG_SERVICIOS;

CREATE OR REPLACE PACKAGE BODY PKG_SERVICIOS AS

    PROCEDURE SP_INSERT_SERVICIO(
        P_ID_HOTEL     IN NUMBER,
        P_NOMBRE       IN VARCHAR2,
        P_DESCRIPCION  IN VARCHAR2,
        P_COSTO        IN NUMBER,
        P_MENSAJE      OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Servicios(ID_Hotel, Nombre_Servicio, Descripcion_Servicio, Costo_Servicio)
        VALUES (P_ID_HOTEL, P_NOMBRE, P_DESCRIPCION, P_COSTO);

        P_MENSAJE := 'Servicio registrado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al registrar servicio: ' || SQLERRM;
    END;


    PROCEDURE SP_UPDATE_SERVICIO(
        P_ID_SERVICIO IN NUMBER,
        P_ID_HOTEL    IN NUMBER,
        P_NOMBRE      IN VARCHAR2,
        P_DESCRIPCION IN VARCHAR2,
        P_COSTO       IN NUMBER,
        P_MENSAJE     OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Servicios
        SET ID_Hotel = P_ID_HOTEL,
            Nombre_Servicio = P_NOMBRE,
            Descripcion_Servicio = P_DESCRIPCION,
            Costo_Servicio = P_COSTO
        WHERE ID_Servicio = P_ID_SERVICIO;

        P_MENSAJE := 'Servicio actualizado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al actualizar servicio: ' || SQLERRM;
    END;


    PROCEDURE SP_DELETE_SERVICIO(
        P_ID_SERVICIO IN NUMBER,
        P_MENSAJE OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Servicios
        WHERE ID_Servicio = P_ID_SERVICIO;

        P_MENSAJE := 'Servicio eliminado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al eliminar servicio: ' || SQLERRM;
    END;

END PKG_SERVICIOS;

--- Servicios Reservaciones
CREATE OR REPLACE PACKAGE PKG_SERVICIOS_RESERVACIONES AS
    PROCEDURE SP_INSERT_SERV_RESERVA(
        P_ID_SERVICIO IN NUMBER,
        P_ID_RESERVA  IN NUMBER,
        P_FECHA       IN DATE,
        P_MENSAJE     OUT VARCHAR2
    );

    PROCEDURE SP_UPDATE_SERV_RESERVA(
        P_ID_SERVICIO IN NUMBER,
        P_ID_RESERVA  IN NUMBER,
        P_FECHA       IN DATE,
        P_MENSAJE     OUT VARCHAR2
    );

    PROCEDURE SP_DELETE_SERV_RESERVA(
        P_ID_SERVICIO IN NUMBER,
        P_ID_RESERVA  IN NUMBER,
        P_MENSAJE     OUT VARCHAR2
    );
END PKG_SERVICIOS_RESERVACIONES;

CREATE OR REPLACE PACKAGE BODY PKG_SERVICIOS_RESERVACIONES AS

    PROCEDURE SP_INSERT_SERV_RESERVA(
        P_ID_SERVICIO IN NUMBER,
        P_ID_RESERVA  IN NUMBER,
        P_FECHA       IN DATE,
        P_MENSAJE     OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Servicios_Reservaciones(ID_Servicio, ID_Reserva, Fecha)
        VALUES (P_ID_SERVICIO, P_ID_RESERVA, P_FECHA);

        P_MENSAJE := 'Servicio asignado a reservación correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al asignar servicio a reservación: ' || SQLERRM;
    END;


    PROCEDURE SP_UPDATE_SERV_RESERVA(
        P_ID_SERVICIO IN NUMBER,
        P_ID_RESERVA  IN NUMBER,
        P_FECHA       IN DATE,
        P_MENSAJE     OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Servicios_Reservaciones
        SET Fecha = P_FECHA
        WHERE ID_Servicio = P_ID_SERVICIO
        AND ID_Reserva = P_ID_RESERVA;

        P_MENSAJE := 'Registro actualizado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al actualizar asignación: ' || SQLERRM;
    END;


    PROCEDURE SP_DELETE_SERV_RESERVA(
        P_ID_SERVICIO IN NUMBER,
        P_ID_RESERVA  IN NUMBER,
        P_MENSAJE OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Servicios_Reservaciones
        WHERE ID_Servicio = P_ID_SERVICIO
        AND ID_Reserva = P_ID_RESERVA;

        P_MENSAJE := 'Registro eliminado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al eliminar asignación: ' || SQLERRM;
    END;

END PKG_SERVICIOS_RESERVACIONES;

--- Servicios habitaciones
CREATE OR REPLACE PACKAGE PKG_SERVICIOS_HABITACIONES AS
    PROCEDURE SP_INSERT_SERV_HAB(
        P_ID_TIPO     IN NUMBER,
        P_NOMBRE      IN VARCHAR2,
        P_DESCRIPCION IN VARCHAR2,
        P_MENSAJE     OUT VARCHAR2
    );

    PROCEDURE SP_UPDATE_SERV_HAB(
        P_ID_SERVICIO_HAB IN NUMBER,
        P_ID_TIPO         IN NUMBER,
        P_NOMBRE          IN VARCHAR2,
        P_DESCRIPCION     IN VARCHAR2,
        P_MENSAJE         OUT VARCHAR2
    );

    PROCEDURE SP_DELETE_SERV_HAB(
        P_ID_SERVICIO_HAB IN NUMBER,
        P_MENSAJE         OUT VARCHAR2
    );
END PKG_SERVICIOS_HABITACIONES;

CREATE OR REPLACE PACKAGE BODY PKG_SERVICIOS_HABITACIONES AS

    PROCEDURE SP_INSERT_SERV_HAB(
        P_ID_TIPO     IN NUMBER,
        P_NOMBRE      IN VARCHAR2,
        P_DESCRIPCION IN VARCHAR2,
        P_MENSAJE OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Servicios_Habitaciones(ID_Tipo_Habitacion, Nombre_Servicio_Habitacion, Descripcion_Servicio_Habitacion)
        VALUES (P_ID_TIPO, P_NOMBRE, P_DESCRIPCION);

        P_MENSAJE := 'Servicio de habitación registrado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al registrar servicio de habitación: ' || SQLERRM;
    END;


    PROCEDURE SP_UPDATE_SERV_HAB(
        P_ID_SERVICIO_HAB IN NUMBER,
        P_ID_TIPO         IN NUMBER,
        P_NOMBRE          IN VARCHAR2,
        P_DESCRIPCION     IN VARCHAR2,
        P_MENSAJE         OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Servicios_Habitaciones
        SET ID_Tipo_Habitacion = P_ID_TIPO,
            Nombre_Servicio_Habitacion = P_NOMBRE,
            Descripcion_Servicio_Habitacion = P_DESCRIPCION
        WHERE ID_Servicio_Habitacion = P_ID_SERVICIO_HAB;

        P_MENSAJE := 'Servicio de habitación actualizado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al actualizar servicio de habitación: ' || SQLERRM;
    END;


    PROCEDURE SP_DELETE_SERV_HAB(
        P_ID_SERVICIO_HAB IN NUMBER,
        P_MENSAJE OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Servicios_Habitaciones
        WHERE ID_Servicio_Habitacion = P_ID_SERVICIO_HAB;

        P_MENSAJE := 'Servicio de habitación eliminado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al eliminar servicio de habitación: ' || SQLERRM;
    END;

END PKG_SERVICIOS_HABITACIONES;

--- Servicios habitaciones tipos habitaciones
CREATE OR REPLACE PACKAGE PKG_SERVICIOSHAB_TIPOSHAB AS
    
    PROCEDURE SP_INSERT_SERVHAB_TIPOHAB(
        P_ID_SERVICIO_HAB IN ServiciosHabitaciones_TiposHabitacion.ID_Servicio_Habitacion%TYPE,
        P_ID_TIPO_HAB IN ServiciosHabitaciones_TiposHabitacion.ID_Tipo_Habitacion%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    );

    PROCEDURE SP_UPDATE_SERVHAB_TIPOHAB(
        P_ID_SERVICIO_HAB IN ServiciosHabitaciones_TiposHabitacion.ID_Servicio_Habitacion%TYPE,
        P_ID_TIPO_HAB IN ServiciosHabitaciones_TiposHabitacion.ID_Tipo_Habitacion%TYPE,
        P_NUEVO_ID_SERVICIO_HAB IN ServiciosHabitaciones_TiposHabitacion.ID_Servicio_Habitacion%TYPE,
        P_NUEVO_ID_TIPO_HAB IN ServiciosHabitaciones_TiposHabitacion.ID_Tipo_Habitacion%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    );

    PROCEDURE SP_DELETE_SERVHAB_TIPOHAB(
        P_ID_SERVICIO_HAB IN ServiciosHabitaciones_TiposHabitacion.ID_Servicio_Habitacion%TYPE,
        P_ID_TIPO_HAB IN ServiciosHabitaciones_TiposHabitacion.ID_Tipo_Habitacion%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    );

END PKG_SERVICIOSHAB_TIPOSHAB;


CREATE OR REPLACE PACKAGE BODY PKG_SERVICIOSHAB_TIPOSHAB AS

    -- INSERT
    PROCEDURE SP_INSERT_SERVHAB_TIPOHAB(
        P_ID_SERVICIO_HAB IN ServiciosHabitaciones_TiposHabitacion.ID_Servicio_Habitacion%TYPE,
        P_ID_TIPO_HAB IN ServiciosHabitaciones_TiposHabitacion.ID_Tipo_Habitacion%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    )
    AS
    BEGIN
        INSERT INTO ServiciosHabitaciones_TiposHabitacion
        (ID_Servicio_Habitacion, ID_Tipo_Habitacion)
        VALUES (P_ID_SERVICIO_HAB, P_ID_TIPO_HAB);

        OUT_MESSAGE := 'Registro insertado correctamente';
    EXCEPTION 
        WHEN OTHERS THEN
            OUT_MESSAGE := SQLERRM;
    END SP_INSERT_SERVHAB_TIPOHAB;


    -- UPDATE
    PROCEDURE SP_UPDATE_SERVHAB_TIPOHAB(
        P_ID_SERVICIO_HAB IN ServiciosHabitaciones_TiposHabitacion.ID_Servicio_Habitacion%TYPE,
        P_ID_TIPO_HAB IN ServiciosHabitaciones_TiposHabitacion.ID_Tipo_Habitacion%TYPE,
        P_NUEVO_ID_SERVICIO_HAB IN ServiciosHabitaciones_TiposHabitacion.ID_Servicio_Habitacion%TYPE,
        P_NUEVO_ID_TIPO_HAB IN ServiciosHabitaciones_TiposHabitacion.ID_Tipo_Habitacion%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    )
    AS
    BEGIN
        UPDATE ServiciosHabitaciones_TiposHabitacion
        SET 
            ID_Servicio_Habitacion = P_NUEVO_ID_SERVICIO_HAB,
            ID_Tipo_Habitacion = P_NUEVO_ID_TIPO_HAB
        WHERE 
            ID_Servicio_Habitacion = P_ID_SERVICIO_HAB
        AND ID_Tipo_Habitacion = P_ID_TIPO_HAB;

        OUT_MESSAGE := 'Registro actualizado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            OUT_MESSAGE := SQLERRM;
    END SP_UPDATE_SERVHAB_TIPOHAB;


    -- DELETE
    PROCEDURE SP_DELETE_SERVHAB_TIPOHAB(
        P_ID_SERVICIO_HAB IN ServiciosHabitaciones_TiposHabitacion.ID_Servicio_Habitacion%TYPE,
        P_ID_TIPO_HAB IN ServiciosHabitaciones_TiposHabitacion.ID_Tipo_Habitacion%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    )
    AS
    BEGIN
        DELETE FROM ServiciosHabitaciones_TiposHabitacion
        WHERE ID_Servicio_Habitacion = P_ID_SERVICIO_HAB
        AND ID_Tipo_Habitacion = P_ID_TIPO_HAB;

        OUT_MESSAGE := 'Registro eliminado correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            OUT_MESSAGE := SQLERRM;
    END SP_DELETE_SERVHAB_TIPOHAB;

END PKG_SERVICIOSHAB_TIPOSHAB;

--- Actividades
CREATE OR REPLACE PACKAGE PKG_ACTIVIDADES AS
    
    PROCEDURE SP_INSERT_ACTIVIDAD(
        P_ID_HOTEL IN Actividades.ID_Hotel%TYPE,
        P_NOMBRE_ACTIVIDAD IN Actividades.Nombre_Actividad%TYPE,
        P_DESCRIPCION IN Actividades.Descripcion_Actividad%TYPE,
        P_CAPACIDAD IN Actividades.Capacidad_Actividad%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    );

    PROCEDURE SP_UPDATE_ACTIVIDAD(
        P_ID_ACTIVIDAD IN Actividades.ID_Actividad%TYPE,
        P_ID_HOTEL IN Actividades.ID_Hotel%TYPE,
        P_NOMBRE_ACTIVIDAD IN Actividades.Nombre_Actividad%TYPE,
        P_DESCRIPCION IN Actividades.Descripcion_Actividad%TYPE,
        P_CAPACIDAD IN Actividades.Capacidad_Actividad%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    );

    PROCEDURE SP_DELETE_ACTIVIDAD(
        P_ID_ACTIVIDAD IN Actividades.ID_Actividad%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    );

END PKG_ACTIVIDADES;

CREATE OR REPLACE PACKAGE BODY PKG_ACTIVIDADES AS

    PROCEDURE SP_INSERT_ACTIVIDAD(
        P_ID_HOTEL IN Actividades.ID_Hotel%TYPE,
        P_NOMBRE_ACTIVIDAD IN Actividades.Nombre_Actividad%TYPE,
        P_DESCRIPCION IN Actividades.Descripcion_Actividad%TYPE,
        P_CAPACIDAD IN Actividades.Capacidad_Actividad%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    )
    AS
    BEGIN
        INSERT INTO Actividades
            (ID_Hotel, Nombre_Actividad, Descripcion_Actividad, Capacidad_Actividad)
        VALUES
            (P_ID_HOTEL, P_NOMBRE_ACTIVIDAD, P_DESCRIPCION, P_CAPACIDAD);

        OUT_MESSAGE := 'Actividad insertada correctamente';
    EXCEPTION WHEN OTHERS THEN
        OUT_MESSAGE := SQLERRM;
    END SP_INSERT_ACTIVIDAD;


    PROCEDURE SP_UPDATE_ACTIVIDAD(
        P_ID_ACTIVIDAD IN Actividades.ID_Actividad%TYPE,
        P_ID_HOTEL IN Actividades.ID_Hotel%TYPE,
        P_NOMBRE_ACTIVIDAD IN Actividades.Nombre_Actividad%TYPE,
        P_DESCRIPCION IN Actividades.Descripcion_Actividad%TYPE,
        P_CAPACIDAD IN Actividades.Capacidad_Actividad%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    )
    AS
    BEGIN
        UPDATE Actividades
        SET 
            ID_Hotel = P_ID_HOTEL,
            Nombre_Actividad = P_NOMBRE_ACTIVIDAD,
            Descripcion_Actividad = P_DESCRIPCION,
            Capacidad_Actividad = P_CAPACIDAD
        WHERE ID_Actividad = P_ID_ACTIVIDAD;

        OUT_MESSAGE := 'Actividad actualizada correctamente';
    EXCEPTION WHEN OTHERS THEN
        OUT_MESSAGE := SQLERRM;
    END SP_UPDATE_ACTIVIDAD;


    PROCEDURE SP_DELETE_ACTIVIDAD(
        P_ID_ACTIVIDAD IN Actividades.ID_Actividad%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    )
    AS
    BEGIN
        DELETE FROM Actividades
        WHERE ID_Actividad = P_ID_ACTIVIDAD;

        OUT_MESSAGE := 'Actividad eliminada correctamente';
    EXCEPTION WHEN OTHERS THEN
        OUT_MESSAGE := SQLERRM;
    END SP_DELETE_ACTIVIDAD;

END PKG_ACTIVIDADES;

--- Roles
CREATE OR REPLACE PACKAGE PKG_ROLES AS
    
    PROCEDURE SP_INSERT_ROL(
        P_NOMBRE IN Roles.Nombre_Rol%TYPE,
        P_DESCRIPCION IN Roles.Descripcion_Rol%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    );

    PROCEDURE SP_UPDATE_ROL(
        P_ID_ROL IN Roles.ID_Rol%TYPE,
        P_NOMBRE IN Roles.Nombre_Rol%TYPE,
        P_DESCRIPCION IN Roles.Descripcion_Rol%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    );

    PROCEDURE SP_DELETE_ROL(
        P_ID_ROL IN Roles.ID_Rol%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    );

END PKG_ROLES;

CREATE OR REPLACE PACKAGE BODY PKG_ROLES AS

    PROCEDURE SP_INSERT_ROL(
        P_NOMBRE IN Roles.Nombre_Rol%TYPE,
        P_DESCRIPCION IN Roles.Descripcion_Rol%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    )
    AS
    BEGIN
        INSERT INTO Roles (Nombre_Rol, Descripcion_Rol)
        VALUES (P_NOMBRE, P_DESCRIPCION);

        OUT_MESSAGE := 'Rol insertado correctamente';
    EXCEPTION WHEN OTHERS THEN
        OUT_MESSAGE := SQLERRM;
    END SP_INSERT_ROL;


    PROCEDURE SP_UPDATE_ROL(
        P_ID_ROL IN Roles.ID_Rol%TYPE,
        P_NOMBRE IN Roles.Nombre_Rol%TYPE,
        P_DESCRIPCION IN Roles.Descripcion_Rol%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    )
    AS
    BEGIN
        UPDATE Roles
        SET Nombre_Rol = P_NOMBRE,
            Descripcion_Rol = P_DESCRIPCION
        WHERE ID_Rol = P_ID_ROL;

        OUT_MESSAGE := 'Rol actualizado correctamente';
    EXCEPTION WHEN OTHERS THEN
        OUT_MESSAGE := SQLERRM;
    END SP_UPDATE_ROL;


    PROCEDURE SP_DELETE_ROL(
        P_ID_ROL IN Roles.ID_Rol%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    )
    AS
    BEGIN
        DELETE FROM Roles
        WHERE ID_Rol = P_ID_ROL;

        OUT_MESSAGE := 'Rol eliminado correctamente';
    EXCEPTION WHEN OTHERS THEN
        OUT_MESSAGE := SQLERRM;
    END SP_DELETE_ROL;

END PKG_ROLES;

--- Empleados
CREATE OR REPLACE PACKAGE PKG_EMPLEADOS AS
    
    PROCEDURE SP_INSERT_EMPLEADO(
        P_ID_HOTEL IN Empleados.ID_Hotel%TYPE,
        P_ID_ROL IN Empleados.ID_Rol%TYPE,
        P_NOMBRE IN Empleados.Nombre%TYPE,
        P_APELLIDO1 IN Empleados.Apellido1%TYPE,
        P_APELLIDO2 IN Empleados.Apellido2%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    );

    PROCEDURE SP_UPDATE_EMPLEADO(
        P_ID_EMPLEADO IN Empleados.ID_Empleado%TYPE,
        P_ID_HOTEL IN Empleados.ID_Hotel%TYPE,
        P_ID_ROL IN Empleados.ID_Rol%TYPE,
        P_NOMBRE IN Empleados.Nombre%TYPE,
        P_APELLIDO1 IN Empleados.Apellido1%TYPE,
        P_APELLIDO2 IN Empleados.Apellido2%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    );

    PROCEDURE SP_DELETE_EMPLEADO(
        P_ID_EMPLEADO IN Empleados.ID_Empleado%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    );

END PKG_EMPLEADOS;

CREATE OR REPLACE PACKAGE BODY PKG_EMPLEADOS AS

    PROCEDURE SP_INSERT_EMPLEADO(
        P_ID_HOTEL IN Empleados.ID_Hotel%TYPE,
        P_ID_ROL IN Empleados.ID_Rol%TYPE,
        P_NOMBRE IN Empleados.Nombre%TYPE,
        P_APELLIDO1 IN Empleados.Apellido1%TYPE,
        P_APELLIDO2 IN Empleados.Apellido2%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    )
    AS
    BEGIN
        INSERT INTO Empleados
            (ID_Hotel, ID_Rol, Nombre, Apellido1, Apellido2)
        VALUES
            (P_ID_HOTEL, P_ID_ROL, P_NOMBRE, P_APELLIDO1, P_APELLIDO2);

        OUT_MESSAGE := 'Empleado insertado correctamente';
    EXCEPTION WHEN OTHERS THEN
        OUT_MESSAGE := SQLERRM;
    END SP_INSERT_EMPLEADO;


    PROCEDURE SP_UPDATE_EMPLEADO(
        P_ID_EMPLEADO IN Empleados.ID_Empleado%TYPE,
        P_ID_HOTEL IN Empleados.ID_Hotel%TYPE,
        P_ID_ROL IN Empleados.ID_Rol%TYPE,
        P_NOMBRE IN Empleados.Nombre%TYPE,
        P_APELLIDO1 IN Empleados.Apellido1%TYPE,
        P_APELLIDO2 IN Empleados.Apellido2%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    )
    AS
    BEGIN
        UPDATE Empleados
        SET 
            ID_Hotel = P_ID_HOTEL,
            ID_Rol = P_ID_ROL,
            Nombre = P_NOMBRE,
            Apellido1 = P_APELLIDO1,
            Apellido2 = P_APELLIDO2
        WHERE ID_Empleado = P_ID_EMPLEADO;

        OUT_MESSAGE := 'Empleado actualizado correctamente';
    EXCEPTION WHEN OTHERS THEN
        OUT_MESSAGE := SQLERRM;
    END SP_UPDATE_EMPLEADO;


    PROCEDURE SP_DELETE_EMPLEADO(
        P_ID_EMPLEADO IN Empleados.ID_Empleado%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    )
    AS
    BEGIN
        DELETE FROM Empleados
        WHERE ID_Empleado = P_ID_EMPLEADO;

        OUT_MESSAGE := 'Empleado eliminado correctamente';
    EXCEPTION WHEN OTHERS THEN
        OUT_MESSAGE := SQLERRM;
    END SP_DELETE_EMPLEADO;

END PKG_EMPLEADOS;

--- Empleados Telefonos
CREATE OR REPLACE PACKAGE PKG_EMPLEADOS_TELEFONOS AS
    
    PROCEDURE SP_INSERT_EMPLEADO_TELEFONO(
        P_ID_EMPLEADO IN Empleados_Telefonos.ID_Empleado%TYPE,
        P_TELEFONO IN Empleados_Telefonos.Telefono_Empleado%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    );

    PROCEDURE SP_UPDATE_EMPLEADO_TELEFONO(
        P_ID_TELEFONO IN Empleados_Telefonos.ID_Empleado_Telefono%TYPE,
        P_ID_EMPLEADO IN Empleados_Telefonos.ID_Empleado%TYPE,
        P_TELEFONO IN Empleados_Telefonos.Telefono_Empleado%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    );

    PROCEDURE SP_DELETE_EMPLEADO_TELEFONO(
        P_ID_TELEFONO IN Empleados_Telefonos.ID_Empleado_Telefono%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    );

END PKG_EMPLEADOS_TELEFONOS;

CREATE OR REPLACE PACKAGE BODY PKG_EMPLEADOS_TELEFONOS AS

    PROCEDURE SP_INSERT_EMPLEADO_TELEFONO(
        P_ID_EMPLEADO IN Empleados_Telefonos.ID_Empleado%TYPE,
        P_TELEFONO IN Empleados_Telefonos.Telefono_Empleado%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    )
    AS
    BEGIN
        INSERT INTO Empleados_Telefonos (ID_Empleado, Telefono_Empleado)
        VALUES (P_ID_EMPLEADO, P_TELEFONO);

        OUT_MESSAGE := 'Teléfono insertado correctamente';
    EXCEPTION WHEN OTHERS THEN
        OUT_MESSAGE := SQLERRM;
    END SP_INSERT_EMPLEADO_TELEFONO;


    PROCEDURE SP_UPDATE_EMPLEADO_TELEFONO(
        P_ID_TELEFONO IN Empleados_Telefonos.ID_Empleado_Telefono%TYPE,
        P_ID_EMPLEADO IN Empleados_Telefonos.ID_Empleado%TYPE,
        P_TELEFONO IN Empleados_Telefonos.Telefono_Empleado%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    )
    AS
    BEGIN
        UPDATE Empleados_Telefonos
        SET 
            ID_Empleado = P_ID_EMPLEADO,
            Telefono_Empleado = P_TELEFONO
        WHERE ID_Empleado_Telefono = P_ID_TELEFONO;

        OUT_MESSAGE := 'Teléfono actualizado correctamente';
    EXCEPTION WHEN OTHERS THEN
        OUT_MESSAGE := SQLERRM;
    END SP_UPDATE_EMPLEADO_TELEFONO;


    PROCEDURE SP_DELETE_EMPLEADO_TELEFONO(
        P_ID_TELEFONO IN Empleados_Telefonos.ID_Empleado_Telefono%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    )
    AS
    BEGIN
        DELETE FROM Empleados_Telefonos
        WHERE ID_Empleado_Telefono = P_ID_TELEFONO;

        OUT_MESSAGE := 'Teléfono eliminado correctamente';
    EXCEPTION WHEN OTHERS THEN
        OUT_MESSAGE := SQLERRM;
    END SP_DELETE_EMPLEADO_TELEFONO;

END PKG_EMPLEADOS_TELEFONOS;

--- Empleados Correos
CREATE OR REPLACE PACKAGE PKG_EMPLEADOS_CORREOS AS

    PROCEDURE SP_INSERT_EMPLEADO_CORREO(
        P_ID_EMPLEADO IN Empleados_Correos.ID_Empleado%TYPE,
        P_CORREO IN Empleados_Correos.Correo_Empleado%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    );

    PROCEDURE SP_UPDATE_EMPLEADO_CORREO(
        P_ID_CORREO IN Empleados_Correos.ID_Empleado_Correo%TYPE,
        P_ID_EMPLEADO IN Empleados_Correos.ID_Empleado%TYPE,
        P_CORREO IN Empleados_Correos.Correo_Empleado%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    );

    PROCEDURE SP_DELETE_EMPLEADO_CORREO(
        P_ID_CORREO IN Empleados_Correos.ID_Empleado_Correo%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    );

END PKG_EMPLEADOS_CORREOS;

CREATE OR REPLACE PACKAGE BODY PKG_EMPLEADOS_CORREOS AS

    PROCEDURE SP_INSERT_EMPLEADO_CORREO(
        P_ID_EMPLEADO IN Empleados_Correos.ID_Empleado%TYPE,
        P_CORREO IN Empleados_Correos.Correo_Empleado%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    )
    AS
    BEGIN
        INSERT INTO Empleados_Correos (ID_Empleado, Correo_Empleado)
        VALUES (P_ID_EMPLEADO, P_CORREO);

        OUT_MESSAGE := 'Correo insertado correctamente';
    EXCEPTION WHEN OTHERS THEN
        OUT_MESSAGE := SQLERRM;
    END SP_INSERT_EMPLEADO_CORREO;


    PROCEDURE SP_UPDATE_EMPLEADO_CORREO(
        P_ID_CORREO IN Empleados_Correos.ID_Empleado_Correo%TYPE,
        P_ID_EMPLEADO IN Empleados_Correos.ID_Empleado%TYPE,
        P_CORREO IN Empleados_Correos.Correo_Empleado%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    )
    AS
    BEGIN
        UPDATE Empleados_Correos
        SET 
            ID_Empleado = P_ID_EMPLEADO,
            Correo_Empleado = P_CORREO
        WHERE ID_Empleado_Correo = P_ID_CORREO;

        OUT_MESSAGE := 'Correo actualizado correctamente';
    EXCEPTION WHEN OTHERS THEN
        OUT_MESSAGE := SQLERRM;
    END SP_UPDATE_EMPLEADO_CORREO;


    PROCEDURE SP_DELETE_EMPLEADO_CORREO(
        P_ID_CORREO IN Empleados_Correos.ID_Empleado_Correo%TYPE,
        OUT_MESSAGE OUT VARCHAR2
    )
    AS
    BEGIN
        DELETE FROM Empleados_Correos
        WHERE ID_Empleado_Correo = P_ID_CORREO;

        OUT_MESSAGE := 'Correo eliminado correctamente';
    EXCEPTION WHEN OTHERS THEN
        OUT_MESSAGE := SQLERRM;
    END SP_DELETE_EMPLEADO_CORREO;

END PKG_EMPLEADOS_CORREOS;

--- Facturas 
CREATE OR REPLACE PACKAGE PKG_FACTURAS AS
    
    -- Insertar Factura
    PROCEDURE SP_INSERT_FACTURA(
        P_ID_RESERVA IN Facturas.ID_Reserva%TYPE,
        P_FECHA      IN Facturas.Fecha%TYPE,
        P_MENSAJE    OUT VARCHAR2
    );

    -- Actualizar Factura
    PROCEDURE SP_UPDATE_FACTURA(
        P_ID_FACTURA IN Facturas.ID_Factura%TYPE,
        P_ID_RESERVA IN Facturas.ID_Reserva%TYPE,
        P_FECHA      IN Facturas.Fecha%TYPE,
        P_MENSAJE    OUT VARCHAR2
    );

    -- Eliminar Factura
    PROCEDURE SP_DELETE_FACTURA(
        P_ID_FACTURA IN Facturas.ID_Factura%TYPE,
        P_MENSAJE    OUT VARCHAR2
    );

END PKG_FACTURAS;

CREATE OR REPLACE PACKAGE BODY PKG_FACTURAS AS

    PROCEDURE SP_INSERT_FACTURA(
        P_ID_RESERVA IN Facturas.ID_Reserva%TYPE,
        P_FECHA      IN Facturas.Fecha%TYPE,
        P_MENSAJE    OUT VARCHAR2
    )
    AS
    BEGIN
        INSERT INTO Facturas (ID_Reserva, Fecha)
        VALUES (P_ID_RESERVA, P_FECHA);

        P_MENSAJE := 'Factura insertada correctamente';
    END SP_INSERT_FACTURA;


    PROCEDURE SP_UPDATE_FACTURA(
        P_ID_FACTURA IN Facturas.ID_Factura%TYPE,
        P_ID_RESERVA IN Facturas.ID_Reserva%TYPE,
        P_FECHA      IN Facturas.Fecha%TYPE,
        P_MENSAJE    OUT VARCHAR2
    )
    AS
    BEGIN
        UPDATE Facturas
        SET ID_Reserva = P_ID_RESERVA,
            Fecha      = P_FECHA
        WHERE ID_Factura = P_ID_FACTURA;

        P_MENSAJE := 'Factura actualizada correctamente';
    END SP_UPDATE_FACTURA;


    PROCEDURE SP_DELETE_FACTURA(
        P_ID_FACTURA IN Facturas.ID_Factura%TYPE,
        P_MENSAJE    OUT VARCHAR2
    )
    AS
    BEGIN
        DELETE FROM Facturas
        WHERE ID_Factura = P_ID_FACTURA;

        P_MENSAJE := 'Factura eliminada correctamente';
    END SP_DELETE_FACTURA;

END PKG_FACTURAS;

--- Detalle factura 
CREATE OR REPLACE PACKAGE PKG_DETALLE_FACTURA AS

    -- Insertar Detalle
    PROCEDURE SP_INSERT_DETALLE_FACTURA(
        P_ID_FACTURA        IN Detalle_Factura.ID_Factura%TYPE,
        P_ID_TIPO_HABIT     IN Detalle_Factura.ID_Tipo_Habitacion%TYPE,
        P_ID_SERVICIOS      IN Detalle_Factura.ID_Servicios%TYPE,
        P_MONTO_TOTAL       IN Detalle_Factura.Monto_Total%TYPE,
        P_MENSAJE           OUT VARCHAR2
    );

    -- Actualizar Detalle
    PROCEDURE SP_UPDATE_DETALLE_FACTURA(
        P_ID_DETALLE        IN Detalle_Factura.ID_DetalleFactura%TYPE,
        P_ID_FACTURA        IN Detalle_Factura.ID_Factura%TYPE,
        P_ID_TIPO_HABIT     IN Detalle_Factura.ID_Tipo_Habitacion%TYPE,
        P_ID_SERVICIOS      IN Detalle_Factura.ID_Servicios%TYPE,
        P_MONTO_TOTAL       IN Detalle_Factura.Monto_Total%TYPE,
        P_MENSAJE           OUT VARCHAR2
    );

    -- Eliminar Detalle
    PROCEDURE SP_DELETE_DETALLE_FACTURA(
        P_ID_DETALLE IN Detalle_Factura.ID_DetalleFactura%TYPE,
        P_MENSAJE    OUT VARCHAR2
    );

END PKG_DETALLE_FACTURA;

CREATE OR REPLACE PACKAGE BODY PKG_DETALLE_FACTURA AS

    PROCEDURE SP_INSERT_DETALLE_FACTURA(
        P_ID_FACTURA        IN Detalle_Factura.ID_Factura%TYPE,
        P_ID_TIPO_HABIT     IN Detalle_Factura.ID_Tipo_Habitacion%TYPE,
        P_ID_SERVICIOS      IN Detalle_Factura.ID_Servicios%TYPE,
        P_MONTO_TOTAL       IN Detalle_Factura.Monto_Total%TYPE,
        P_MENSAJE           OUT VARCHAR2
    )
    AS
    BEGIN
        INSERT INTO Detalle_Factura
            (ID_Factura, ID_Tipo_Habitacion, ID_Servicios, Monto_Total)
        VALUES
            (P_ID_FACTURA, P_ID_TIPO_HABIT, P_ID_SERVICIOS, P_MONTO_TOTAL);

        P_MENSAJE := 'Detalle de factura insertado correctamente';
    END SP_INSERT_DETALLE_FACTURA;


    PROCEDURE SP_UPDATE_DETALLE_FACTURA(
        P_ID_DETALLE        IN Detalle_Factura.ID_DetalleFactura%TYPE,
        P_ID_FACTURA        IN Detalle_Factura.ID_Factura%TYPE,
        P_ID_TIPO_HABIT     IN Detalle_Factura.ID_Tipo_Habitacion%TYPE,
        P_ID_SERVICIOS      IN Detalle_Factura.ID_Servicios%TYPE,
        P_MONTO_TOTAL       IN Detalle_Factura.Monto_Total%TYPE,
        P_MENSAJE           OUT VARCHAR2
    )
    AS
    BEGIN
        UPDATE Detalle_Factura
        SET ID_Factura        = P_ID_FACTURA,
            ID_Tipo_Habitacion = P_ID_TIPO_HABIT,
            ID_Servicios        = P_ID_SERVICIOS,
            Monto_Total         = P_MONTO_TOTAL
        WHERE ID_DetalleFactura = P_ID_DETALLE;

        P_MENSAJE := 'Detalle de factura actualizado correctamente';
    END SP_UPDATE_DETALLE_FACTURA;


    PROCEDURE SP_DELETE_DETALLE_FACTURA(
        P_ID_DETALLE IN Detalle_Factura.ID_DetalleFactura%TYPE,
        P_MENSAJE    OUT VARCHAR2
    )
    AS
    BEGIN
        DELETE FROM Detalle_Factura
        WHERE ID_DetalleFactura = P_ID_DETALLE;

        P_MENSAJE := 'Detalle de factura eliminado correctamente';
    END SP_DELETE_DETALLE_FACTURA;

END PKG_DETALLE_FACTURA;

--- Paquetes para el FRONT 

--- Reservaciones 
CREATE OR REPLACE PACKAGE PKG_RESERVACIONES_FRONT AS

    -- Insertar Reservación
    PROCEDURE SP_INSERT_RESERVA(
        P_FECHA_ENTRADA   IN Reservaciones.Fecha_Entrada%TYPE,
        P_FECHA_SALIDA    IN Reservaciones.Fecha_Salida%TYPE,
        P_CEDULA          IN Reservaciones.Cedula%TYPE,
        P_TIPO_HABITACION IN NUMBER,
        P_MENSAJE         OUT VARCHAR2
    );

    -- Actualizar Reservación
    PROCEDURE SP_UPDATE_RESERVA(
        P_ID_RESERVA      IN Reservaciones.ID_Reserva%TYPE,
        P_FECHA_ENTRADA   IN Reservaciones.Fecha_Entrada%TYPE,
        P_FECHA_SALIDA    IN Reservaciones.Fecha_Salida%TYPE,
        P_CEDULA          IN Reservaciones.Cedula%TYPE,
        P_TIPO_HABITACION IN NUMBER,
        P_MENSAJE         OUT VARCHAR2
    );

    -- Eliminar Reservación
    PROCEDURE SP_DELETE_RESERVA(
        P_ID_RESERVA IN Reservaciones.ID_Reserva%TYPE,
        P_MENSAJE    OUT VARCHAR2
    );

END PKG_RESERVACIONES_FRONT;

CREATE OR REPLACE PACKAGE BODY PKG_RESERVACIONES_FRONT AS

    PROCEDURE SP_INSERT_RESERVA(
        P_FECHA_ENTRADA   IN Reservaciones.Fecha_Entrada%TYPE,
        P_FECHA_SALIDA    IN Reservaciones.Fecha_Salida%TYPE,
        P_CEDULA          IN Reservaciones.Cedula%TYPE,
        P_TIPO_HABITACION IN NUMBER,
        P_MENSAJE         OUT VARCHAR2
    )
    AS
    BEGIN
        INSERT INTO Reservaciones (Fecha_Entrada, Fecha_Salida, Cedula)
        VALUES (P_FECHA_ENTRADA, P_FECHA_SALIDA, P_CEDULA);

        P_MENSAJE := 'Reservación creada correctamente';
    END SP_INSERT_RESERVA;

    PROCEDURE SP_UPDATE_RESERVA(
        P_ID_RESERVA      IN Reservaciones.ID_Reserva%TYPE,
        P_FECHA_ENTRADA   IN Reservaciones.Fecha_Entrada%TYPE,
        P_FECHA_SALIDA    IN Reservaciones.Fecha_Salida%TYPE,
        P_CEDULA          IN Reservaciones.Cedula%TYPE,
        P_TIPO_HABITACION IN NUMBER,
        P_MENSAJE         OUT VARCHAR2
    )
    AS
    BEGIN
        UPDATE Reservaciones
        SET Fecha_Entrada = P_FECHA_ENTRADA,
            Fecha_Salida  = P_FECHA_SALIDA,
            Cedula        = P_CEDULA
        WHERE ID_Reserva = P_ID_RESERVA;

        P_MENSAJE := 'Reservación actualizada correctamente';
    END SP_UPDATE_RESERVA;



    PROCEDURE SP_DELETE_RESERVA(
        P_ID_RESERVA IN Reservaciones.ID_Reserva%TYPE,
        P_MENSAJE    OUT VARCHAR2
    )
    AS
    BEGIN
        DELETE FROM Reservaciones
        WHERE ID_Reserva = P_ID_RESERVA;

        P_MENSAJE := 'Reservación eliminada correctamente';
    END SP_DELETE_RESERVA;

END PKG_RESERVACIONES_FRONT;

--- Checks in out
CREATE OR REPLACE PACKAGE PKG_CHECK_IN_OUT_FRONT AS

    -- Insertar Check-in/out con devolución
    PROCEDURE SP_INSERT_CHECK(
        P_ID_RESERVA     IN Check_in_out.ID_Reserva%TYPE,
        P_FECHA_ENTRADA  IN Check_in_out.Fecha_Entrada%TYPE,
        P_HORA_ENTRADA   IN Check_in_out.Hora_Entrada%TYPE,
        P_FECHA_SALIDA   IN Check_in_out.Fecha_Salida%TYPE,
        P_HORA_SALIDA    IN Check_in_out.Hora_Salida%TYPE,
        P_DEVOLUCION     IN Check_in_out_Devoluciones.Devolucion%TYPE,
        P_MENSAJE        OUT VARCHAR2
    );

    -- Actualizar Check-in/out y su devolución
    PROCEDURE SP_UPDATE_CHECK(
        P_ID_CHECK       IN Check_in_out.ID_Check%TYPE,
        P_ID_RESERVA     IN Check_in_out.ID_Reserva%TYPE,
        P_FECHA_ENTRADA  IN Check_in_out.Fecha_Entrada%TYPE,
        P_HORA_ENTRADA   IN Check_in_out.Hora_Entrada%TYPE,
        P_FECHA_SALIDA   IN Check_in_out.Fecha_Salida%TYPE,
        P_HORA_SALIDA    IN Check_in_out.Hora_Salida%TYPE,
        P_DEVOLUCION     IN Check_in_out_Devoluciones.Devolucion%TYPE,
        P_MENSAJE        OUT VARCHAR2
    );

    -- Eliminar Check-in/out y su devolución
    PROCEDURE SP_DELETE_CHECK(
        P_ID_CHECK IN Check_in_out.ID_Check%TYPE,
        P_MENSAJE  OUT VARCHAR2
    );

END PKG_CHECK_IN_OUT_FRONT;

CREATE OR REPLACE PACKAGE BODY PKG_CHECK_IN_OUT_FRONT AS

    PROCEDURE SP_INSERT_CHECK(
        P_ID_RESERVA     IN Check_in_out.ID_Reserva%TYPE,
        P_FECHA_ENTRADA  IN Check_in_out.Fecha_Entrada%TYPE,
        P_HORA_ENTRADA   IN Check_in_out.Hora_Entrada%TYPE,
        P_FECHA_SALIDA   IN Check_in_out.Fecha_Salida%TYPE,
        P_HORA_SALIDA    IN Check_in_out.Hora_Salida%TYPE,
        P_DEVOLUCION     IN Check_in_out_Devoluciones.Devolucion%TYPE,
        P_MENSAJE        OUT VARCHAR2
    )
    AS
        V_ID_CHECK NUMBER;
    BEGIN
        -- Insert principal en check_in_out
        INSERT INTO Check_in_out (Hora_Entrada, Hora_Salida, Fecha_Entrada, Fecha_Salida, ID_Reserva)
        VALUES (P_HORA_ENTRADA, P_HORA_SALIDA, P_FECHA_ENTRADA, P_FECHA_SALIDA, P_ID_RESERVA)
        RETURNING ID_Check INTO V_ID_CHECK;

        -- Insert de devolución (si viene)
        IF P_DEVOLUCION IS NOT NULL THEN
            INSERT INTO Check_in_out_Devoluciones (ID_Check, Devolucion)
            VALUES (V_ID_CHECK, P_DEVOLUCION);
        END IF;

        P_MENSAJE := 'Check-in/out creado correctamente';
    END SP_INSERT_CHECK;

    PROCEDURE SP_UPDATE_CHECK(
        P_ID_CHECK       IN Check_in_out.ID_Check%TYPE,
        P_ID_RESERVA     IN Check_in_out.ID_Reserva%TYPE,
        P_FECHA_ENTRADA  IN Check_in_out.Fecha_Entrada%TYPE,
        P_HORA_ENTRADA   IN Check_in_out.Hora_Entrada%TYPE,
        P_FECHA_SALIDA   IN Check_in_out.Fecha_Salida%TYPE,
        P_HORA_SALIDA    IN Check_in_out.Hora_Salida%TYPE,
        P_DEVOLUCION     IN Check_in_out_Devoluciones.Devolucion%TYPE,
        P_MENSAJE        OUT VARCHAR2
    )
    AS
    BEGIN
        -- Actualizar registro principal
        UPDATE Check_in_out
        SET Hora_Entrada  = P_HORA_ENTRADA,
            Hora_Salida   = P_HORA_SALIDA,
            Fecha_Entrada = P_FECHA_ENTRADA,
            Fecha_Salida  = P_FECHA_SALIDA,
            ID_Reserva    = P_ID_RESERVA
        WHERE ID_Check = P_ID_CHECK;

        -- Actualizar o insertar devolución
        MERGE INTO Check_in_out_Devoluciones d
        USING (SELECT P_ID_CHECK AS ID_CHECK FROM dual) src
        ON (d.ID_Check = src.ID_CHECK)
        WHEN MATCHED THEN
            UPDATE SET d.Devolucion = P_DEVOLUCION
        WHEN NOT MATCHED THEN
            INSERT (ID_Check, Devolucion)
            VALUES (P_ID_CHECK, P_DEVOLUCION);

        P_MENSAJE := 'Check-in/out actualizado correctamente';
    END SP_UPDATE_CHECK;

    PROCEDURE SP_DELETE_CHECK(
        P_ID_CHECK IN Check_in_out.ID_Check%TYPE,
        P_MENSAJE  OUT VARCHAR2
    )
    AS
    BEGIN
        -- Eliminar devoluciones primero
        DELETE FROM Check_in_out_Devoluciones
        WHERE ID_Check = P_ID_CHECK;

        -- Luego eliminar check in/out
        DELETE FROM Check_in_out
        WHERE ID_Check = P_ID_CHECK;

        P_MENSAJE := 'Check-in/out eliminado correctamente';
    END SP_DELETE_CHECK;

END PKG_CHECK_IN_OUT_FRONT;

--- Clientes
CREATE OR REPLACE PACKAGE PKG_CLIENTES_FRONT AS
    PROCEDURE SP_INSERT_CLIENTE(
        P_CEDULA          IN Clientes.Cedula%TYPE,
        P_NOMBRE          IN Clientes.Nombre_Cliente%TYPE,
        P_APELLIDO1       IN Clientes.Primer_Apellido%TYPE,
        P_APELLIDO2       IN Clientes.Segundo_Apellido%TYPE,
        P_TELEFONO        IN Clientes_Telefonos.Telefono_Cliente%TYPE,
        P_CORREO          IN Clientes_Correos.Correo_Cliente%TYPE,
        P_MENSAJE         OUT VARCHAR2
    );

    PROCEDURE SP_UPDATE_CLIENTE(
        P_CEDULA          IN Clientes.Cedula%TYPE,
        P_NOMBRE          IN Clientes.Nombre_Cliente%TYPE,
        P_APELLIDO1       IN Clientes.Primer_Apellido%TYPE,
        P_APELLIDO2       IN Clientes.Segundo_Apellido%TYPE,
        P_TELEFONO        IN Clientes_Telefonos.Telefono_Cliente%TYPE,
        P_CORREO          IN Clientes_Correos.Correo_Cliente%TYPE,
        P_MENSAJE         OUT VARCHAR2
    );

    PROCEDURE SP_DELETE_CLIENTE(
        P_CEDULA  IN Clientes.Cedula%TYPE,
        P_MENSAJE OUT VARCHAR2
    );
END PKG_CLIENTES_FRONT;

CREATE OR REPLACE PACKAGE BODY PKG_CLIENTES_FRONT AS

    PROCEDURE SP_INSERT_CLIENTE(
        P_CEDULA          IN Clientes.Cedula%TYPE,
        P_NOMBRE          IN Clientes.Nombre_Cliente%TYPE,
        P_APELLIDO1       IN Clientes.Primer_Apellido%TYPE,
        P_APELLIDO2       IN Clientes.Segundo_Apellido%TYPE,
        P_TELEFONO        IN Clientes_Telefonos.Telefono_Cliente%TYPE,
        P_CORREO          IN Clientes_Correos.Correo_Cliente%TYPE,
        P_MENSAJE         OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Clientes (Cedula, Nombre_Cliente, Primer_Apellido, Segundo_Apellido)
        VALUES (P_CEDULA, P_NOMBRE, P_APELLIDO1, P_APELLIDO2);

        INSERT INTO Clientes_Telefonos (Cedula, Telefono_Cliente)
        VALUES (P_CEDULA, P_TELEFONO);

        INSERT INTO Clientes_Correos (Cedula, Correo_Cliente)
        VALUES (P_CEDULA, P_CORREO);

        P_MENSAJE := 'Cliente insertado correctamente.';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al insertar cliente: ' || SQLERRM;
    END SP_INSERT_CLIENTE;
    
    PROCEDURE SP_UPDATE_CLIENTE(
        P_CEDULA          IN Clientes.Cedula%TYPE,
        P_NOMBRE          IN Clientes.Nombre_Cliente%TYPE,
        P_APELLIDO1       IN Clientes.Primer_Apellido%TYPE,
        P_APELLIDO2       IN Clientes.Segundo_Apellido%TYPE,
        P_TELEFONO        IN Clientes_Telefonos.Telefono_Cliente%TYPE,
        P_CORREO          IN Clientes_Correos.Correo_Cliente%TYPE,
        P_MENSAJE         OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Clientes
        SET Nombre_Cliente  = P_NOMBRE,
            Primer_Apellido = P_APELLIDO1,
            Segundo_Apellido = P_APELLIDO2
        WHERE Cedula = P_CEDULA;

        UPDATE Clientes_Telefonos
        SET Telefono_Cliente = P_TELEFONO
        WHERE Cedula = P_CEDULA;

        UPDATE Clientes_Correos
        SET Correo_Cliente = P_CORREO
        WHERE Cedula = P_CEDULA;

        P_MENSAJE := 'Cliente actualizado correctamente.';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al actualizar cliente: ' || SQLERRM;
    END SP_UPDATE_CLIENTE;

    PROCEDURE SP_DELETE_CLIENTE(
        P_CEDULA  IN Clientes.Cedula%TYPE,
        P_MENSAJE OUT VARCHAR2
    ) AS
    BEGIN
        -- Primero borrar hijos por FK
        DELETE FROM Clientes_Telefonos
        WHERE Cedula = P_CEDULA;

        DELETE FROM Clientes_Correos
        WHERE Cedula = P_CEDULA;

        DELETE FROM Clientes
        WHERE Cedula = P_CEDULA;

        P_MENSAJE := 'Cliente eliminado correctamente.';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al eliminar cliente: ' || SQLERRM;
    END SP_DELETE_CLIENTE;


END PKG_CLIENTES_FRONT;


--- Hoteles 
CREATE OR REPLACE PACKAGE PKG_HOTELES_FRONT AS

PROCEDURE SP_INSERT_HOTEL(
    P_NOMBRE            IN Hoteles.Nombre_Hotel%TYPE,
    P_TELEFONO          IN Hoteles_Telefonos.Telefono_Hotel%TYPE,
    P_CORREO            IN Hoteles_Correos.Correo_Hotel%TYPE,
    P_PROVINCIA         IN Provincias.Nombre_Provincia%TYPE,
    P_CANTON            IN Cantones.Nombre_Canton%TYPE,
    P_DISTRITO          IN Distritos.Nombre_Distrito%TYPE,
    P_DIRECCION_EXACTA  IN Direcciones.Direccion_Exacta%TYPE,
    P_RUTA_IMAGEN       IN Hoteles.Ruta_Imagen%TYPE,
    P_MENSAJE           OUT VARCHAR2
);

PROCEDURE SP_UPDATE_HOTEL(
    P_ID_HOTEL          IN Hoteles.ID_Hotel%TYPE,
    P_NOMBRE            IN Hoteles.Nombre_Hotel%TYPE,
    P_TELEFONO          IN Hoteles_Telefonos.Telefono_Hotel%TYPE,
    P_CORREO            IN Hoteles_Correos.Correo_Hotel%TYPE,
    P_PROVINCIA         IN Provincias.Nombre_Provincia%TYPE,
    P_CANTON            IN Cantones.Nombre_Canton%TYPE,
    P_DISTRITO          IN Distritos.Nombre_Distrito%TYPE,
    P_DIRECCION_EXACTA  IN Direcciones.Direccion_Exacta%TYPE,
    P_RUTA_IMAGEN       IN Hoteles.Ruta_Imagen%TYPE,
    P_MENSAJE           OUT VARCHAR2
);

PROCEDURE SP_DELETE_HOTEL(
    P_ID_HOTEL  IN Hoteles.ID_Hotel%TYPE,
    P_MENSAJE   OUT VARCHAR2
);


END PKG_HOTELES_FRONT;


CREATE OR REPLACE PACKAGE BODY PKG_HOTELES_FRONT AS


PROCEDURE SP_INSERT_HOTEL(
    P_NOMBRE            IN Hoteles.Nombre_Hotel%TYPE,
    P_TELEFONO          IN Hoteles_Telefonos.Telefono_Hotel%TYPE,
    P_CORREO            IN Hoteles_Correos.Correo_Hotel%TYPE,
    P_PROVINCIA         IN Provincias.Nombre_Provincia%TYPE,
    P_CANTON            IN Cantones.Nombre_Canton%TYPE,
    P_DISTRITO          IN Distritos.Nombre_Distrito%TYPE,
    P_DIRECCION_EXACTA  IN Direcciones.Direccion_Exacta%TYPE,
    P_RUTA_IMAGEN       IN Hoteles.Ruta_Imagen%TYPE,
    P_MENSAJE           OUT VARCHAR2
) AS
    V_ID_PROVINCIA Provincias.ID_Provincia%TYPE;
    V_ID_CANTON    Cantones.ID_Canton%TYPE;
    V_ID_DISTRITO  Distritos.ID_Distrito%TYPE;
    V_ID_DIRECCION Direcciones.ID_Direccion%TYPE;
    V_ID_HOTEL     Hoteles.ID_Hotel%TYPE;
BEGIN
    -- Obtener o crear provincia
    BEGIN
        SELECT ID_PROVINCIA INTO V_ID_PROVINCIA
        FROM PROVINCIAS
        WHERE NOMBRE_PROVINCIA = P_PROVINCIA;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            INSERT INTO PROVINCIAS(NOMBRE_PROVINCIA)
            VALUES(P_PROVINCIA)
            RETURNING ID_PROVINCIA INTO V_ID_PROVINCIA;
    END;

    -- Obtener o crear cantón
    BEGIN
        SELECT ID_CANTON INTO V_ID_CANTON
        FROM CANTONES
        WHERE NOMBRE_CANTON = P_CANTON
          AND ID_PROVINCIA = V_ID_PROVINCIA;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            INSERT INTO CANTONES(ID_PROVINCIA, NOMBRE_CANTON)
            VALUES(V_ID_PROVINCIA, P_CANTON)
            RETURNING ID_CANTON INTO V_ID_CANTON;
    END;

    -- Obtener o crear distrito
    BEGIN
        SELECT ID_DISTRITO INTO V_ID_DISTRITO
        FROM DISTRITOS
        WHERE NOMBRE_DISTRITO = P_DISTRITO
          AND ID_CANTON = V_ID_CANTON;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            INSERT INTO DISTRITOS(ID_CANTON, NOMBRE_DISTRITO)
            VALUES(V_ID_CANTON, P_DISTRITO)
            RETURNING ID_DISTRITO INTO V_ID_DISTRITO;
    END;

    -- Insertar dirección
    INSERT INTO DIRECCIONES(DIRECCION_EXACTA, ID_DISTRITO)
    VALUES(P_DIRECCION_EXACTA, V_ID_DISTRITO)
    RETURNING ID_DIRECCION INTO V_ID_DIRECCION;

    -- Insertar hotel (ID autogenerado)
    INSERT INTO HOTELES(NOMBRE_HOTEL, ID_DIRECCION, RUTA_IMAGEN)
    VALUES(P_NOMBRE, V_ID_DIRECCION, P_RUTA_IMAGEN)
    RETURNING ID_HOTEL INTO V_ID_HOTEL;

    -- Insertar teléfono
    INSERT INTO HOTELES_TELEFONOS(ID_HOTEL, TELEFONO_HOTEL)
    VALUES(V_ID_HOTEL, P_TELEFONO);

    -- Insertar correo
    INSERT INTO HOTELES_CORREOS(ID_HOTEL, CORREO_HOTEL)
    VALUES(V_ID_HOTEL, P_CORREO);

    P_MENSAJE := 'Hotel insertado correctamente.';
EXCEPTION
    WHEN OTHERS THEN
        P_MENSAJE := 'Error al insertar hotel: ' || SQLERRM;
END SP_INSERT_HOTEL;

-- SP_UPDATE_HOTEL similar, adaptando a nombres de provincia/cantón/distrito
PROCEDURE SP_UPDATE_HOTEL(
    P_ID_HOTEL          IN Hoteles.ID_Hotel%TYPE,
    P_NOMBRE            IN Hoteles.Nombre_Hotel%TYPE,
    P_TELEFONO          IN Hoteles_Telefonos.Telefono_Hotel%TYPE,
    P_CORREO            IN Hoteles_Correos.Correo_Hotel%TYPE,
    P_PROVINCIA         IN Provincias.Nombre_Provincia%TYPE,
    P_CANTON            IN Cantones.Nombre_Canton%TYPE,
    P_DISTRITO          IN Distritos.Nombre_Distrito%TYPE,
    P_DIRECCION_EXACTA  IN Direcciones.Direccion_Exacta%TYPE,
    P_RUTA_IMAGEN       IN Hoteles.Ruta_Imagen%TYPE,
    P_MENSAJE           OUT VARCHAR2
) AS
    V_ID_PROVINCIA Provincias.ID_Provincia%TYPE;
    V_ID_CANTON    Cantones.ID_Canton%TYPE;
    V_ID_DISTRITO  Distritos.ID_Distrito%TYPE;
    V_ID_DIRECCION Direcciones.ID_Direccion%TYPE;
BEGIN
    -- Obtener IDs según nombres (asumir que existen)
    SELECT ID_PROVINCIA INTO V_ID_PROVINCIA
    FROM PROVINCIAS WHERE NOMBRE_PROVINCIA = P_PROVINCIA;

    SELECT ID_CANTON INTO V_ID_CANTON
    FROM CANTONES
    WHERE NOMBRE_CANTON = P_CANTON AND ID_PROVINCIA = V_ID_PROVINCIA;

    SELECT ID_DISTRITO INTO V_ID_DISTRITO
    FROM DISTRITOS
    WHERE NOMBRE_DISTRITO = P_DISTRITO AND ID_CANTON = V_ID_CANTON;

    -- Actualizar dirección
    SELECT ID_DIRECCION INTO V_ID_DIRECCION FROM HOTELES WHERE ID_HOTEL = P_ID_HOTEL;

    UPDATE DIRECCIONES
    SET DIRECCION_EXACTA = P_DIRECCION_EXACTA,
        ID_DISTRITO      = V_ID_DISTRITO
    WHERE ID_DIRECCION = V_ID_DIRECCION;

    -- Actualizar hotel
    UPDATE HOTELES
    SET NOMBRE_HOTEL = P_NOMBRE,
        RUTA_IMAGEN  = P_RUTA_IMAGEN
    WHERE ID_HOTEL = P_ID_HOTEL;

    -- Actualizar teléfono
    UPDATE HOTELES_TELEFONOS
    SET TELEFONO_HOTEL = P_TELEFONO
    WHERE ID_HOTEL = P_ID_HOTEL;

    -- Actualizar correo
    UPDATE HOTELES_CORREOS
    SET CORREO_HOTEL = P_CORREO
    WHERE ID_HOTEL = P_ID_HOTEL;

    P_MENSAJE := 'Hotel actualizado correctamente.';
EXCEPTION
    WHEN OTHERS THEN
        P_MENSAJE := 'Error al actualizar hotel: ' || SQLERRM;
END SP_UPDATE_HOTEL;

-- SP_DELETE_HOTEL
PROCEDURE SP_DELETE_HOTEL(
    P_ID_HOTEL  IN Hoteles.ID_Hotel%TYPE,
    P_MENSAJE   OUT VARCHAR2
) AS
    V_ID_DIRECCION Direcciones.ID_Direccion%TYPE;
BEGIN
    SELECT ID_DIRECCION INTO V_ID_DIRECCION
    FROM HOTELES
    WHERE ID_HOTEL = P_ID_HOTEL;

    DELETE FROM HOTELES_TELEFONOS WHERE ID_HOTEL = P_ID_HOTEL;
    DELETE FROM HOTELES_CORREOS WHERE ID_HOTEL = P_ID_HOTEL;
    DELETE FROM HOTELES WHERE ID_HOTEL = P_ID_HOTEL;
    DELETE FROM DIRECCIONES WHERE ID_DIRECCION = V_ID_DIRECCION;

    P_MENSAJE := 'Hotel eliminado correctamente.';
EXCEPTION
    WHEN OTHERS THEN
        P_MENSAJE := 'Error al eliminar hotel: ' || SQLERRM;
END SP_DELETE_HOTEL;


END PKG_HOTELES_FRONT;
/
--- Habitaciones
CREATE OR REPLACE PACKAGE PKG_HABITACIONES_FRONT AS
    
    PROCEDURE SP_INSERT_HABITACION(
        P_ID_TIPO_HABITACION IN Habitaciones.ID_Tipo_Habitacion%TYPE,
        P_NUMERO_HABITACION  IN Habitaciones.Numero_Habitacion%TYPE,
        P_ID_HOTEL           IN Habitaciones.ID_Hotel%TYPE,
        P_ESTADO             IN Habitaciones.Estado_Habitacion%TYPE,
        P_MENSAJE            OUT VARCHAR2
    );

    PROCEDURE SP_UPDATE_HABITACION(
        P_ID_HABITACION      IN Habitaciones.ID_Habitacion%TYPE,
        P_ID_TIPO_HABITACION IN Habitaciones.ID_Tipo_Habitacion%TYPE,
        P_NUMERO_HABITACION  IN Habitaciones.Numero_Habitacion%TYPE,
        P_ID_HOTEL           IN Habitaciones.ID_Hotel%TYPE,
        P_ESTADO             IN Habitaciones.Estado_Habitacion%TYPE,
        P_MENSAJE            OUT VARCHAR2
    );

    PROCEDURE SP_DELETE_HABITACION(
        P_ID_HABITACION IN Habitaciones.ID_Habitacion%TYPE,
        P_MENSAJE       OUT VARCHAR2
    );

END PKG_HABITACIONES_FRONT;

CREATE OR REPLACE PACKAGE BODY PKG_HABITACIONES_FRONT AS

    PROCEDURE SP_INSERT_HABITACION(
        P_ID_TIPO_HABITACION IN Habitaciones.ID_Tipo_Habitacion%TYPE,
        P_NUMERO_HABITACION  IN Habitaciones.Numero_Habitacion%TYPE,
        P_ID_HOTEL           IN Habitaciones.ID_Hotel%TYPE,
        P_ESTADO             IN Habitaciones.Estado_Habitacion%TYPE,
        P_MENSAJE            OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Habitaciones(
            ID_Tipo_Habitacion,
            Numero_Habitacion,
            ID_Hotel,
            Estado_Habitacion
        )
        VALUES(
            P_ID_TIPO_HABITACION,
            P_NUMERO_HABITACION,
            P_ID_HOTEL,
            P_ESTADO
        );

        P_MENSAJE := 'Habitación insertada correctamente.';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al insertar habitación: ' || SQLERRM;
    END SP_INSERT_HABITACION;

    PROCEDURE SP_UPDATE_HABITACION(
        P_ID_HABITACION      IN Habitaciones.ID_Habitacion%TYPE,
        P_ID_TIPO_HABITACION IN Habitaciones.ID_Tipo_Habitacion%TYPE,
        P_NUMERO_HABITACION  IN Habitaciones.Numero_Habitacion%TYPE,
        P_ID_HOTEL           IN Habitaciones.ID_Hotel%TYPE,
        P_ESTADO             IN Habitaciones.Estado_Habitacion%TYPE,
        P_MENSAJE            OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Habitaciones
        SET ID_Tipo_Habitacion = P_ID_TIPO_HABITACION,
            Numero_Habitacion  = P_NUMERO_HABITACION,
            ID_Hotel           = P_ID_HOTEL,
            Estado_Habitacion  = P_ESTADO
        WHERE ID_Habitacion = P_ID_HABITACION;

        P_MENSAJE := 'Habitación actualizada correctamente.';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al actualizar habitación: ' || SQLERRM;
    END SP_UPDATE_HABITACION;

    PROCEDURE SP_DELETE_HABITACION(
        P_ID_HABITACION IN Habitaciones.ID_Habitacion%TYPE,
        P_MENSAJE       OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Habitaciones
        WHERE ID_Habitacion = P_ID_HABITACION;

        P_MENSAJE := 'Habitación eliminada correctamente.';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al eliminar habitación: ' || SQLERRM;
    END SP_DELETE_HABITACION;

END PKG_HABITACIONES_FRONT;

--- Empleados 
CREATE OR REPLACE PACKAGE PKG_EMPLEADOS_FRONT AS

    PROCEDURE SP_INSERT_EMPLEADO(
        P_ID_EMPLEADO   IN Empleados.ID_Empleado%TYPE,
        P_ID_ROL        IN Empleados.ID_Rol%TYPE,
        P_NOMBRE        IN Empleados.Nombre%TYPE,
        P_APELLIDO1     IN Empleados.Apellido1%TYPE,
        P_APELLIDO2     IN Empleados.Apellido2%TYPE,
        P_TELEFONO      IN VARCHAR2,
        P_CORREO        IN VARCHAR2,
        P_MENSAJE       OUT VARCHAR2
    );

    PROCEDURE SP_UPDATE_EMPLEADO(
        P_ID_EMPLEADO   IN Empleados.ID_Empleado%TYPE,
        P_ID_ROL        IN Empleados.ID_Rol%TYPE,
        P_NOMBRE        IN Empleados.Nombre%TYPE,
        P_APELLIDO1     IN Empleados.Apellido1%TYPE,
        P_APELLIDO2     IN Empleados.Apellido2%TYPE,
        P_TELEFONO      IN VARCHAR2,
        P_CORREO        IN VARCHAR2,
        P_MENSAJE       OUT VARCHAR2
    );

    PROCEDURE SP_DELETE_EMPLEADO(
        P_ID_EMPLEADO IN Empleados.ID_Empleado%TYPE,
        P_MENSAJE     OUT VARCHAR2
    );

END PKG_EMPLEADOS_FRONT;

CREATE OR REPLACE PACKAGE BODY PKG_EMPLEADOS_FRONT AS

    PROCEDURE SP_INSERT_EMPLEADO(
        P_ID_EMPLEADO   IN Empleados.ID_Empleado%TYPE,
        P_ID_ROL        IN Empleados.ID_Rol%TYPE,
        P_NOMBRE        IN Empleados.Nombre%TYPE,
        P_APELLIDO1     IN Empleados.Apellido1%TYPE,
        P_APELLIDO2     IN Empleados.Apellido2%TYPE,
        P_TELEFONO      IN VARCHAR2,
        P_CORREO        IN VARCHAR2,
        P_MENSAJE       OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Empleados(ID_Empleado, ID_Rol, Nombre, Apellido1, Apellido2)
        VALUES (P_ID_EMPLEADO, P_ID_ROL, P_NOMBRE, P_APELLIDO1, P_APELLIDO2);

        INSERT INTO Empleados_Telefonos(ID_Empleado, Telefono_Empleado)
        VALUES (P_ID_EMPLEADO, P_TELEFONO);

        INSERT INTO Empleados_Correos(ID_Empleado, Correo_Empleado)
        VALUES (P_ID_EMPLEADO, P_CORREO);

        P_MENSAJE := 'Empleado insertado correctamente.';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al insertar empleado: ' || SQLERRM;
    END SP_INSERT_EMPLEADO;

    PROCEDURE SP_UPDATE_EMPLEADO(
        P_ID_EMPLEADO   IN Empleados.ID_Empleado%TYPE,
        P_ID_ROL        IN Empleados.ID_Rol%TYPE,
        P_NOMBRE        IN Empleados.Nombre%TYPE,
        P_APELLIDO1     IN Empleados.Apellido1%TYPE,
        P_APELLIDO2     IN Empleados.Apellido2%TYPE,
        P_TELEFONO      IN VARCHAR2,
        P_CORREO        IN VARCHAR2,
        P_MENSAJE       OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Empleados
        SET ID_Rol = P_ID_ROL,
            Nombre = P_NOMBRE,
            Apellido1 = P_APELLIDO1,
            Apellido2 = P_APELLIDO2
        WHERE ID_Empleado = P_ID_EMPLEADO;

        UPDATE Empleados_Telefonos
        SET Telefono_Empleado = P_TELEFONO
        WHERE ID_Empleado = P_ID_EMPLEADO;

        UPDATE Empleados_Correos
        SET Correo_Empleado = P_CORREO
        WHERE ID_Empleado = P_ID_EMPLEADO;

        P_MENSAJE := 'Empleado actualizado correctamente.';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al actualizar empleado: ' || SQLERRM;
    END SP_UPDATE_EMPLEADO;

    PROCEDURE SP_DELETE_EMPLEADO(
        P_ID_EMPLEADO IN Empleados.ID_Empleado%TYPE,
        P_MENSAJE     OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Empleados_Telefonos WHERE ID_Empleado = P_ID_EMPLEADO;
        DELETE FROM Empleados_Correos   WHERE ID_Empleado = P_ID_EMPLEADO;
        DELETE FROM Empleados           WHERE ID_Empleado = P_ID_EMPLEADO;

        P_MENSAJE := 'Empleado eliminado correctamente.';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al eliminar empleado: ' || SQLERRM;
    END SP_DELETE_EMPLEADO;

END PKG_EMPLEADOS_FRONT;

--- Actividades 
CREATE OR REPLACE PACKAGE PKG_ACTIVIDADES_FRONT AS
    

    PROCEDURE SP_AGREGAR_ACTIVIDAD(
        P_ID_HOTEL            IN NUMBER,
        P_NOMBRE_ACTIVIDAD    IN VARCHAR2,
        P_DESCRIPCION         IN VARCHAR2,
        P_CAPACIDAD           IN NUMBER,
        P_RUTA_IMAGEN         IN VARCHAR2,
        P_MENSAJE             OUT VARCHAR2
    );


    PROCEDURE SP_EDITAR_ACTIVIDAD(
        P_ID_ACTIVIDAD        IN NUMBER,
        P_ID_HOTEL            IN NUMBER,
        P_NOMBRE_ACTIVIDAD    IN VARCHAR2,
        P_DESCRIPCION         IN VARCHAR2,
        P_CAPACIDAD           IN NUMBER,
        P_RUTA_IMAGEN         IN VARCHAR2,
        P_MENSAJE             OUT VARCHAR2
    );

    PROCEDURE SP_ELIMINAR_ACTIVIDAD(
        P_ID_ACTIVIDAD IN NUMBER,
        P_MENSAJE      OUT VARCHAR2
    );

END PKG_ACTIVIDADES_FRONT;
/



CREATE OR REPLACE PACKAGE BODY PKG_ACTIVIDADES_FRONT AS

    PROCEDURE SP_AGREGAR_ACTIVIDAD(
        P_ID_HOTEL            IN NUMBER,
        P_NOMBRE_ACTIVIDAD    IN VARCHAR2,
        P_DESCRIPCION         IN VARCHAR2,
        P_CAPACIDAD           IN NUMBER,
        P_RUTA_IMAGEN         IN VARCHAR2,
        P_MENSAJE             OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Actividades 
            (ID_Hotel, Nombre_Actividad, Descripcion_Actividad, Capacidad_Actividad, ruta_imagen)
        VALUES 
            (P_ID_HOTEL, P_NOMBRE_ACTIVIDAD, P_DESCRIPCION, P_CAPACIDAD, P_RUTA_IMAGEN);

        P_MENSAJE := 'Actividad agregada correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al agregar actividad: ' || SQLERRM;
    END SP_AGREGAR_ACTIVIDAD;


    PROCEDURE SP_EDITAR_ACTIVIDAD(
        P_ID_ACTIVIDAD        IN NUMBER,
        P_ID_HOTEL            IN NUMBER,
        P_NOMBRE_ACTIVIDAD    IN VARCHAR2,
        P_DESCRIPCION         IN VARCHAR2,
        P_CAPACIDAD           IN NUMBER,
        P_RUTA_IMAGEN         IN VARCHAR2,
        P_MENSAJE             OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Actividades
        SET 
            ID_Hotel = P_ID_HOTEL,
            Nombre_Actividad = P_NOMBRE_ACTIVIDAD,
            Descripcion_Actividad = P_DESCRIPCION,
            Capacidad_Actividad = P_CAPACIDAD,
            ruta_imagen = P_RUTA_IMAGEN
        WHERE ID_Actividad = P_ID_ACTIVIDAD;

        P_MENSAJE := 'Actividad actualizada correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al actualizar actividad: ' || SQLERRM;
    END SP_EDITAR_ACTIVIDAD;


    PROCEDURE SP_ELIMINAR_ACTIVIDAD(
        P_ID_ACTIVIDAD IN NUMBER,
        P_MENSAJE      OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Actividades
        WHERE ID_Actividad = P_ID_ACTIVIDAD;

        P_MENSAJE := 'Actividad eliminada correctamente';
    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al eliminar actividad: ' || SQLERRM;
    END SP_ELIMINAR_ACTIVIDAD;

END PKG_ACTIVIDADES_FRONT;
/

--- Servicios 
-- PACKAGE
CREATE OR REPLACE PACKAGE PKG_SERVICIOS_FRONT AS

    PROCEDURE SP_AGREGAR_SERVICIO(
        P_ID_HOTEL         IN NUMBER,
        P_NOMBRE_SERVICIO  IN VARCHAR2,
        P_DESCRIPCION      IN VARCHAR2,
        P_COSTO_SERVICIO   IN NUMBER,
        P_RUTA_IMAGEN      IN VARCHAR2,
        P_MENSAJE          OUT VARCHAR2
    );

    PROCEDURE SP_EDITAR_SERVICIO(
        P_ID_SERVICIO      IN NUMBER,
        P_ID_HOTEL         IN NUMBER,
        P_NOMBRE_SERVICIO  IN VARCHAR2,
        P_DESCRIPCION      IN VARCHAR2,
        P_COSTO_SERVICIO   IN NUMBER,
        P_RUTA_IMAGEN      IN VARCHAR2,
        P_MENSAJE          OUT VARCHAR2
    );

    PROCEDURE SP_ELIMINAR_SERVICIO(
        P_ID_SERVICIO IN NUMBER,
        P_MENSAJE     OUT VARCHAR2
    );

END PKG_SERVICIOS_FRONT;
/

CREATE OR REPLACE PACKAGE BODY PKG_SERVICIOS_FRONT AS

    PROCEDURE SP_AGREGAR_SERVICIO(
        P_ID_HOTEL         IN NUMBER,
        P_NOMBRE_SERVICIO  IN VARCHAR2,
        P_DESCRIPCION      IN VARCHAR2,
        P_COSTO_SERVICIO   IN NUMBER,
        P_RUTA_IMAGEN      IN VARCHAR2,
        P_MENSAJE          OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Servicios(
            ID_HOTEL,
            NOMBRE_SERVICIO,
            DESCRIPCION_SERVICIO,
            COSTO_SERVICIO,
            RUTA_IMAGEN
        ) VALUES (
            P_ID_HOTEL,
            P_NOMBRE_SERVICIO,
            P_DESCRIPCION,
            P_COSTO_SERVICIO,
            P_RUTA_IMAGEN
        );

        P_MENSAJE := 'Servicio agregado correctamente';

    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al agregar servicio: ' || SQLERRM;
    END SP_AGREGAR_SERVICIO;

    PROCEDURE SP_EDITAR_SERVICIO(
        P_ID_SERVICIO      IN NUMBER,
        P_ID_HOTEL         IN NUMBER,
        P_NOMBRE_SERVICIO  IN VARCHAR2,
        P_DESCRIPCION      IN VARCHAR2,
        P_COSTO_SERVICIO   IN NUMBER,
        P_RUTA_IMAGEN      IN VARCHAR2,
        P_MENSAJE          OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Servicios
        SET 
            ID_HOTEL = P_ID_HOTEL,
            NOMBRE_SERVICIO = P_NOMBRE_SERVICIO,
            DESCRIPCION_SERVICIO = P_DESCRIPCION,
            COSTO_SERVICIO = P_COSTO_SERVICIO,
            RUTA_IMAGEN = P_RUTA_IMAGEN
        WHERE ID_SERVICIO = P_ID_SERVICIO;

        P_MENSAJE := 'Servicio actualizado correctamente';

    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al actualizar servicio: ' || SQLERRM;
    END SP_EDITAR_SERVICIO;

    PROCEDURE SP_ELIMINAR_SERVICIO(
        P_ID_SERVICIO IN NUMBER,
        P_MENSAJE     OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Servicios
        WHERE ID_SERVICIO = P_ID_SERVICIO;

        P_MENSAJE := 'Servicio eliminado correctamente';

    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al eliminar servicio: ' || SQLERRM;
    END SP_ELIMINAR_SERVICIO;

END PKG_SERVICIOS_FRONT;
/




commit;


--- Servicios Habitaciones
CREATE OR REPLACE PACKAGE PKG_SERVICIOS_HABITACIONES_FRONT AS
    
    PROCEDURE SP_AGREGAR_SERVICIO_HAB(
        P_ID_SERVICIO_HABITACION   IN NUMBER,
        P_ID_TIPO_HABITACION       IN NUMBER,
        P_NOMBRE_SERVICIO          IN VARCHAR2,
        P_DESCRIPCION              IN VARCHAR2,
        P_RUTA_IMAGEN              IN VARCHAR2,
        P_MENSAJE                  OUT VARCHAR2
    );

    PROCEDURE SP_EDITAR_SERVICIO_HAB(
        P_ID_SERVICIO_HABITACION   IN NUMBER,
        P_ID_TIPO_HABITACION       IN NUMBER,
        P_NOMBRE_SERVICIO          IN VARCHAR2,
        P_DESCRIPCION              IN VARCHAR2,
        P_RUTA_IMAGEN              IN VARCHAR2,
        P_MENSAJE                  OUT VARCHAR2
    );

    PROCEDURE SP_ELIMINAR_SERVICIO_HAB(
        P_ID_SERVICIO_HABITACION   IN NUMBER,
        P_MENSAJE                  OUT VARCHAR2
    );

END PKG_SERVICIOS_HABITACIONES_FRONT;

CREATE OR REPLACE PACKAGE BODY PKG_SERVICIOS_HABITACIONES_FRONT AS

    PROCEDURE SP_AGREGAR_SERVICIO_HAB(
        P_ID_SERVICIO_HABITACION   IN NUMBER,
        P_ID_TIPO_HABITACION       IN NUMBER,
        P_NOMBRE_SERVICIO          IN VARCHAR2,
        P_DESCRIPCION              IN VARCHAR2,
        P_RUTA_IMAGEN              IN VARCHAR2,
        P_MENSAJE                  OUT VARCHAR2
    ) AS
    BEGIN
        INSERT INTO Servicios_Habitaciones
            (ID_Servicio_Habitacion,
             ID_Tipo_Habitacion,
             Nombre_Servicio_Habitacion,
             Descripcion_Servicio_Habitacion,
             Ruta_Imagen)
        VALUES
            (P_ID_SERVICIO_HABITACION,
             P_ID_TIPO_HABITACION,
             P_NOMBRE_SERVICIO,
             P_DESCRIPCION,
             P_RUTA_IMAGEN);

        P_MENSAJE := 'Servicio de habitación agregado correctamente';

    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al agregar servicio de habitación: ' || SQLERRM;
    END SP_AGREGAR_SERVICIO_HAB;



    PROCEDURE SP_EDITAR_SERVICIO_HAB(
        P_ID_SERVICIO_HABITACION   IN NUMBER,
        P_ID_TIPO_HABITACION       IN NUMBER,
        P_NOMBRE_SERVICIO          IN VARCHAR2,
        P_DESCRIPCION              IN VARCHAR2,
        P_RUTA_IMAGEN              IN VARCHAR2,
        P_MENSAJE                  OUT VARCHAR2
    ) AS
    BEGIN
        UPDATE Servicios_Habitaciones
        SET 
            ID_Tipo_Habitacion        = P_ID_TIPO_HABITACION,
            Nombre_Servicio_Habitacion = P_NOMBRE_SERVICIO,
            Descripcion_Servicio_Habitacion = P_DESCRIPCION,
            Ruta_Imagen               = P_RUTA_IMAGEN
        WHERE ID_Servicio_Habitacion = P_ID_SERVICIO_HABITACION;

        P_MENSAJE := 'Servicio de habitación actualizado correctamente';

    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al actualizar servicio de habitación: ' || SQLERRM;
    END SP_EDITAR_SERVICIO_HAB;



    PROCEDURE SP_ELIMINAR_SERVICIO_HAB(
        P_ID_SERVICIO_HABITACION   IN NUMBER,
        P_MENSAJE                  OUT VARCHAR2
    ) AS
    BEGIN
        DELETE FROM Servicios_Habitaciones
        WHERE ID_Servicio_Habitacion = P_ID_SERVICIO_HABITACION;

        P_MENSAJE := 'Servicio de habitación eliminado correctamente';

    EXCEPTION
        WHEN OTHERS THEN
            P_MENSAJE := 'Error al eliminar servicio de habitación: ' || SQLERRM;
    END SP_ELIMINAR_SERVICIO_HAB;

END PKG_SERVICIOS_HABITACIONES_FRONT;

--- Reserva servicios 
CREATE OR REPLACE PROCEDURE SP_AGREGAR_SERVICIO_RESERVA (
    P_ID_SERVICIO   IN NUMBER,
    P_ID_RESERVA    IN NUMBER,
    P_FECHA         IN DATE,
    P_MENSAJE       OUT VARCHAR2
)
AS
BEGIN
    INSERT INTO Servicios_Reservaciones (
        ID_Servicio,
        ID_Reserva,
        Fecha
    )
    VALUES (
        P_ID_SERVICIO,
        P_ID_RESERVA,
        P_FECHA
    );

    P_MENSAJE := 'Servicio agregado a la reservación correctamente';

EXCEPTION
    WHEN OTHERS THEN
        P_MENSAJE := 'Error al agregar servicio a reservación: ' || SQLERRM;
END SP_AGREGAR_SERVICIO_RESERVA;

--Funciones
--Funcion para mostrar una reserva mediante un id
CREATE OR REPLACE FUNCTION FN_GET_RESERVA (
    P_ID_RESERVA IN Reservaciones.ID_Reserva%TYPE
)
RETURN SYS_REFCURSOR
AS
    V_CURSOR SYS_REFCURSOR;
BEGIN
    OPEN V_CURSOR FOR
        SELECT ID_Reserva,
               Fecha_Entrada,
               Fecha_Salida,
               Cedula
        FROM Reservaciones
        WHERE ID_Reserva = P_ID_RESERVA;

    RETURN V_CURSOR;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN NULL;
    WHEN OTHERS THEN
        RETURN NULL;
END;

--Funcion para mostrar un cliente mediante una cedula

CREATE OR REPLACE FUNCTION FN_GET_CLIENTE (
    P_CEDULA IN Clientes.Cedula%TYPE
)
RETURN SYS_REFCURSOR
AS V_CURSOR SYS_REFCURSOR;
BEGIN
    OPEN V_CURSOR FOR
        SELECT Cedula,
               Nombre_Cliente,
               Primer_Apellido,
               Segundo_Apellido
        FROM Clientes
        WHERE Cedula = P_CEDULA;

    RETURN V_CURSOR;
EXCEPTION
    WHEN NO_DATA_FOUND THEN RETURN NULL;
    WHEN OTHERS THEN RETURN NULL;
END;

-- Obtener Hotel
CREATE OR REPLACE FUNCTION FN_GET_HOTEL (
    P_ID_HOTEL IN Hoteles.ID_Hotel%TYPE
)
RETURN SYS_REFCURSOR
AS 
    V_CURSOR SYS_REFCURSOR;
BEGIN
    OPEN V_CURSOR FOR
        SELECT * FROM V_HOTELES_DETALLE
        WHERE ID_Hotel = P_ID_HOTEL;
    RETURN V_CURSOR;
EXCEPTION
    WHEN NO_DATA_FOUND THEN 
        RETURN NULL;
    WHEN OTHERS THEN 
        RETURN NULL;
END FN_GET_HOTEL;
/

--Funcion para mostrar un empleado mediante un id
CREATE OR REPLACE FUNCTION FN_GET_EMPLEADO (
    P_ID_EMPLEADO IN Empleados.ID_Empleado%TYPE
)
RETURN SYS_REFCURSOR
AS V_CURSOR SYS_REFCURSOR;
BEGIN
    OPEN V_CURSOR FOR
        SELECT ID_Empleado,
               ID_Hotel,
               ID_Rol,
               Nombre,
               Apellido1,
               Apellido2
        FROM Empleados
        WHERE ID_Empleado = P_ID_EMPLEADO;

    RETURN V_CURSOR;
EXCEPTION
    WHEN NO_DATA_FOUND THEN RETURN NULL;
    WHEN OTHERS THEN RETURN NULL;
END;

--Funcion para mostrar un servicio mediante un id
create or replace FUNCTION FN_GET_SERVICIO (
    P_ID_SERVICIO IN Servicios.ID_Servicio%TYPE
)
RETURN SYS_REFCURSOR
AS 
    V_CURSOR SYS_REFCURSOR;
BEGIN
    OPEN V_CURSOR FOR
        SELECT ID_Servicio, ID_Hotel, Nombre_Servicio, Descripcion_Servicio, Costo_Servicio, ruta_imagen   
        FROM Servicios
        WHERE ID_Servicio = P_ID_SERVICIO;
    RETURN V_CURSOR;
EXCEPTION
    WHEN NO_DATA_FOUND THEN 
        RETURN NULL;
        WHEN OTHERS THEN RETURN NULL;
END;

--Funcion para mostrar una habitacion mediante un id

CREATE OR REPLACE FUNCTION FN_GET_HABITACION (
    P_ID_HABITACION IN Habitaciones.ID_Habitacion%TYPE
)
RETURN SYS_REFCURSOR
AS V_CURSOR SYS_REFCURSOR;
BEGIN
    OPEN V_CURSOR FOR
        SELECT ID_Habitacion,
               Numero_Habitacion,
               Estado_Habitacion,
               ID_Tipo_Habitacion,
               ID_Reserva,
               ID_Hotel
        FROM Habitaciones
        WHERE ID_Habitacion = P_ID_HABITACION;

    RETURN V_CURSOR;
EXCEPTION
    WHEN NO_DATA_FOUND THEN RETURN NULL;
    WHEN OTHERS THEN RETURN NULL;
END;

--Funcion para mostrar un Check-In-Out mediante un id 

CREATE OR REPLACE FUNCTION FN_GET_CHECK (
    P_ID_CHECK IN Check_in_out.ID_Check%TYPE
)
RETURN SYS_REFCURSOR
AS V_CURSOR SYS_REFCURSOR;
BEGIN
    OPEN V_CURSOR FOR
        SELECT ID_Check,
               Hora_Entrada,
               Hora_Salida,
               Fecha_Entrada,
               Fecha_Salida,
               ID_Reserva
        FROM Check_in_out
        WHERE ID_Check = P_ID_CHECK;

    RETURN V_CURSOR;
EXCEPTION
    WHEN NO_DATA_FOUND THEN RETURN NULL;
    WHEN OTHERS THEN RETURN NULL;
END;

--Funcion para mostrar una factura mediante un id

CREATE OR REPLACE FUNCTION FN_GET_FACTURA (
    P_ID_FACTURA IN Facturas.ID_Factura%TYPE
)
RETURN SYS_REFCURSOR
AS V_CURSOR SYS_REFCURSOR;
BEGIN
    OPEN V_CURSOR FOR
        SELECT ID_Factura,
               ID_Reserva,
               Fecha
        FROM Facturas
        WHERE ID_Factura = P_ID_FACTURA;

    RETURN V_CURSOR;
EXCEPTION
    WHEN NO_DATA_FOUND THEN RETURN NULL;
    WHEN OTHERS THEN RETURN NULL;
END;

--Vistas

--- Reservas 
CREATE OR REPLACE VIEW V_Reservas_Detalle AS
SELECT
    R.ID_Reserva,
    H.ID_Tipo_Habitacion,
    R.Fecha_Entrada,
    R.Fecha_Salida,
    C.Cedula
FROM Reservaciones R
LEFT JOIN Habitaciones H 
    ON R.ID_Reserva = H.ID_Reserva
INNER JOIN Clientes C
    ON R.Cedula = C.Cedula;
    
--- Check in out
CREATE OR REPLACE VIEW V_CheckInOut_Detalle AS
SELECT
    C.ID_Check,
    R.ID_Reserva,
    C.Fecha_Entrada,
    C.Fecha_Salida,
    C.Hora_Entrada,
    C.Hora_Salida,
    D.Devolucion
FROM Check_in_out C
INNER JOIN Reservaciones R
    ON C.ID_Reserva = R.ID_Reserva
LEFT JOIN Check_in_out_Devoluciones D
    ON C.ID_Check = D.ID_Check;
    
--- Clientes
CREATE OR REPLACE VIEW V_Clientes_Detalle AS
SELECT
    C.Cedula,
    C.Nombre_Cliente,
    C.Primer_Apellido,
    C.Segundo_Apellido,
    T.Telefono_Cliente,
    E.Correo_Cliente
FROM Clientes C
LEFT JOIN Clientes_Telefonos T
    ON C.Cedula = T.Cedula
LEFT JOIN Clientes_Correos E
    ON C.Cedula = E.Cedula;
    
--- Hoteles 
CREATE OR REPLACE VIEW V_Hoteles_Detalle AS
SELECT
    H.ID_Hotel,
    H.Nombre_Hotel,
    T.Telefono_Hotel,
    C.Correo_Hotel,
    D.Direccion_Exacta,
    H.Ruta_Imagen,            
    P.Nombre_Provincia,         
    Can.Nombre_Canton,         
    Dis.Nombre_Distrito        
FROM Hoteles H
LEFT JOIN Hoteles_Telefonos T 
    ON H.ID_Hotel = T.ID_Hotel
LEFT JOIN Hoteles_Correos C 
    ON H.ID_Hotel = C.ID_Hotel
INNER JOIN Direcciones D 
    ON H.ID_Direccion = D.ID_Direccion
INNER JOIN Distritos Dis 
    ON D.ID_Distrito = Dis.ID_Distrito
INNER JOIN Cantones Can 
    ON Dis.ID_Canton = Can.ID_Canton
INNER JOIN Provincias P 
    ON Can.ID_Provincia = P.ID_Provincia;
--- Habitaciones
CREATE OR REPLACE VIEW V_Habitaciones_Detalle AS
SELECT
    H.ID_Tipo_Habitacion,
    H.Numero_Habitacion,
    H.ID_Hotel
FROM Habitaciones H
INNER JOIN Tipos_Habitaciones TH
    ON H.ID_Tipo_Habitacion = TH.ID_Tipo_Habitacion
INNER JOIN Hoteles HT
    ON H.ID_Hotel = HT.ID_Hotel;
    
--- Empleados 
CREATE OR REPLACE VIEW V_Empleados_Detalle AS
SELECT
    E.ID_Empleado,
    E.ID_Rol,
    E.Nombre,
    E.Apellido1 AS Primer_Apellido,
    E.Apellido2 AS Segundo_Apellido,
    T.Telefono_Empleado,
    C.Correo_Empleado
FROM Empleados E
LEFT JOIN Empleados_Telefonos T
    ON E.ID_Empleado = T.ID_Empleado
LEFT JOIN Empleados_Correos C
    ON E.ID_Empleado = C.ID_Empleado;

--- Actividades 
CREATE OR REPLACE VIEW V_Actividades_Detalle AS
SELECT
    A.ID_Hotel,
    A.ID_Actividad,
    A.Nombre_Actividad,
    A.Descripcion_Actividad,
    A.Capacidad_Actividad,
    A.ruta_imagen
FROM Actividades A
INNER JOIN Hoteles H
    ON A.ID_Hotel = H.ID_Hotel;
    
--- Servicios 
CREATE OR REPLACE VIEW V_SERVICIOS_DETALLE AS
SELECT
    S.ID_SERVICIO AS ID_SERVICIO,
    S.ID_HOTEL AS ID_HOTEL,
    S.NOMBRE_SERVICIO AS NOMBRE_SERVICIO,
    S.DESCRIPCION_SERVICIO AS DESCRIPCION_SERVICIO,
    S.COSTO_SERVICIO  AS COSTO_SERVICIO,
    S.RUTA_IMAGEN     AS RUTA_IMAGEN
FROM SERVICIOS S
INNER JOIN HOTELES H
    ON S.ID_HOTEL = H.ID_HOTEL;


--- Servicios habitaciones  
CREATE OR REPLACE VIEW VW_SERVICIOS_HABITACIONES AS
SELECT
    SH.ID_Servicio_Habitacion,
    SH.ID_Tipo_Habitacion,
    TH.Nombre_Tipo_Habitacion,
    SH.Nombre_Servicio_Habitacion,
    SH.Descripcion_Servicio_Habitacion,
    SH.Ruta_Imagen
FROM Servicios_Habitaciones SH
INNER JOIN Tipos_Habitaciones TH
    ON SH.ID_Tipo_Habitacion = TH.ID_Tipo_Habitacion;
/
--CODIGO PARA AGREGAR LINEAS FALTANTES--
ALTER TABLE Hoteles ADD Ruta_Imagen VARCHAR2(255);
ALTER TABLE SERVICIOS_HABITACIONESADD RUTA_IMAGEN VARCHAR2(300);
ALTER TABLE Servicios ADD ruta_imagen VARCHAR2(300);
ALTER TABLE Actividades ADD ruta_imagen VARCHAR2(300);
commit;
