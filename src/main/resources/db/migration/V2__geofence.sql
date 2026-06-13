-- Cerca virtual (geofence) da empresa e localização das batidas de ponto
ALTER TABLE empresa ADD COLUMN latitude     DOUBLE PRECISION;
ALTER TABLE empresa ADD COLUMN longitude    DOUBLE PRECISION;
ALTER TABLE empresa ADD COLUMN raio_metros  INTEGER;

ALTER TABLE ponto ADD COLUMN latitude  DOUBLE PRECISION;
ALTER TABLE ponto ADD COLUMN longitude DOUBLE PRECISION;
