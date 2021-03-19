CREATE TABLE clientes (
    dni VARCHAR(20) NOT NULL,
    nombrec VARCHAR(50) NOT NULL,
    direccion VARCHAR(200),
    telefono VARCHAR(15) NOT NULL,
    fechanac DATE
);

ALTER TABLE clientes 
ADD CONSTRAINT pk_dni 
PRIMARY KEY (dni);

CREATE TABLE productos(
    idproductos VARCHAR(10) NOT NULL,
    nombrep VARCHAR(30) NOT NULL,
    descripcion VARCHAR(100) NOT NULL,
    preciop FLOAT NOT NULL,
    talla VARCHAR(10) NOT NULL,
    stock INT NOT NULL,
    color VARCHAR(20)
);

ALTER TABLE productos
ADD CONSTRAINT pk_idproductos
PRIMARY KEY(idproductos);

ALTER TABLE productos
ADD CONSTRAINT uq_nombrep
UNIQUE(nombrep);


CREATE TABLE compras(
    nrocompra INT AUTO_INCREMENT,
    fechaemision DATE NOT NULL,
    tipomoneda CHAR(1) NOT NULL,
    tipopago CHAR(1) NOT NULL,
    descripcionc VARCHAR(255) NOT NULL,
    dniclientes VARCHAR(20) NOT NULL,
    PRIMARY KEY(nrocompra)
);

ALTER TABLE compras 
ADD CONSTRAINT fk_dniclientes 
FOREIGN KEY (dniclientes) 
REFERENCES clientes(dni);

CREATE TABLE detalles_compras(
    nrocompra INT NOT NULL,
    idproductos VARCHAR(10) NOT NULL
);

ALTER TABLE detalles_compras
ADD CONSTRAINT pk_nro_compra_idproducto
PRIMARY KEY(nrocompra,idproductos);

ALTER TABLE detalles_compras
ADD CONSTRAINT fk_nro_compra
FOREIGN KEY(nrocompra)
REFERENCES compras(nrocompra);

ALTER TABLE detalles_compras
ADD CONSTRAINT fk_idproductos
FOREIGN KEY(idproductos)
REFERENCES productos(idproductos);


