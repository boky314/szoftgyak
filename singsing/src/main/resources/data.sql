INSERT INTO user(username,password, registration) VALUES('admin', '{noop}admin', CURRENT_TIMESTAMP());
INSERT INTO user(username,password, registration) VALUES('admin2', '{noop}admin2', DATEADD('MONTH',-2, CURRENT_DATE));

INSERT INTO password(username,password) VALUES('admin2', '{noop}admin2');
INSERT INTO password(username,password) VALUES('admin', '{noop}admin');

INSERT INTO auditlog(user,dateTime,changeType,change) VALUES('admin', CURRENT_TIMESTAMP(),'CREATE','BELA');

INSERT INTO prisonguard VALUES('Jozsi');
INSERT INTO prisonguard VALUES('Bela');