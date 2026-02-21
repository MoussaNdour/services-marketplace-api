-- 1. GESTION DU CYCLE DE VIE DES PRESTATIONS
-- Ajout d'un statut pour suivre l'évolution d'une demande
ALTER TABLE askingService ADD COLUMN status varchar(50) DEFAULT 'PENDING';
-- PENDING (en attente), ACCEPTED (accepté), COMPLETED (terminé), CANCELLED (annulé)

-- Ajout d'une date prévue pour la prestation
ALTER TABLE askingService ADD COLUMN scheduledAt timestamp;

-- 2. AUDIT ET TRAÇABILITÉ (Crucial pour le Master et le CV)
-- Savoir quand une demande ou un utilisateur a été créé
ALTER TABLE askingService ADD COLUMN createdAt timestamp DEFAULT current_timestamp;
ALTER TABLE users ADD COLUMN createdAt timestamp DEFAULT current_timestamp;
ALTER TABLE users ADD COLUMN updatedAt timestamp;

-- 3. LOCALISATION (Pour la recherche par ville)
ALTER TABLE provider ADD COLUMN city varchar(100) DEFAULT 'Dakar';
ALTER TABLE client ADD COLUMN city varchar(100) DEFAULT 'Dakar';
CREATE INDEX idx_provider_city ON provider(city);

-- 4. CORRECTION DU SYSTÈME DE NOTES (Reviews)
-- On lie la note à l'intervention réelle (askingService)
-- pour savoir quel client a noté quel service précis.
ALTER TABLE review ADD COLUMN askingServiceId int REFERENCES askingService(id);

-- 5. DISPONIBILITÉS (Pour que les clients sachent quand commander)
CREATE TABLE availability (
    id serial PRIMARY KEY,
    providerId int NOT NULL REFERENCES provider(id),
    dayOfWeek int NOT NULL, -- 1 (Lundi) à 7 (Dimanche)
    startTime time NOT NULL,
    endTime time NOT NULL,
    UNIQUE(providerId, dayOfWeek, startTime)
);