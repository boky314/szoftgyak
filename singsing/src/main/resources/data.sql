INSERT INTO user(username,password, registration, user_group) VALUES('admin', '{noop}admin', CURRENT_TIMESTAMP(), 'ADMIN');
INSERT INTO user(username,password, registration, user_group) VALUES('admin2', '{noop}admin2', DATEADD('MONTH',-2, CURRENT_DATE), 'ADMIN');
INSERT INTO user(username,password, registration, user_group) VALUES('Bela', '{noop}admin', CURRENT_TIMESTAMP(), 'GUARD');

INSERT INTO password(username,password) VALUES('admin2', '{noop}admin2');
INSERT INTO password(username,password) VALUES('admin', '{noop}admin');

INSERT INTO auditlog(user,dateTime,changeType,change) VALUES('admin', CURRENT_TIMESTAMP(),'CREATE','BELA');

INSERT INTO prisonguard VALUES('Jozsi');
INSERT INTO prisonguard VALUES('Bela');

INSERT INTO area(name, SECURITY_LEVEL) VALUES( 'SUPER_GUARDED', 'Medium');

INSERT INTO prisoncell(space,cell_desc, floor, area_id) VALUES( 4, ' Tágas', 1, 1 );
INSERT INTO prisoncell(space,cell_desc, floor, area_id) VALUES( 1, ' Zárka', -1, 1 );