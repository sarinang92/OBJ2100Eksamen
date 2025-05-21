# ARBEID GJORT 5/20 (Maksim)

---

## Systemvedlikeholdfunksjoner 

- Eksportere sletteloggen  
- Ta sikkerhetskopi av databasen  
- Rydde midlertidige data (ubetalte billetter)  
- Overvåke aktive bestillinger  

---

## Hvordan teste funksjonene

### Krav:
- Krever `config.properties`-fil med riktig `pg_dump_path` for å kjøre backup. (vanlig path: C:\Program Files\PostgreSQL\17\bin\pg_dump.exe)
- Backup funksjonen skaper en backup fil inne i `db_backups/` mappen.
- Backup blir lagret som `kino_backup.dump`
- Kjør `TestDB.java`** for å teste alle systemvedlikehold-funksjonene manuelt.
- Kan kommentere vekk kode for å teste funksjonene en etter en.

---

## Brukerroller og rettigheter
- Enkelte funksjoner krever administrative rettigheter i databasen.
- Testbrukeren `"Case"` må ha `SELECT`, `DELETE` og `USAGE`-tilganger på relevante tabeller og sekvenser.

---

# Ting som må gjøres

---

## Oppgave 2 - Kinobetjent (billettsalg) - Albert 
- Hentes fra mappen KinoSystem_Part2_Complete, men det er uklart om alle delene er ferdig:
- Bør teste og sjekke om slettinger.dat faktisk oppdateres og slettingen skjer riktig før visningsstart

---

## Oppgave 3 - Kunde (Kinopublikum) - Amanial
- Mappen kunde_bestilling i onedrive til Sarina inneholder visst arbeidet, som må bli sett på
- Bør sjekke om dette systemet fungerer og er koblet til databasen korrekt

---

## GUI (bonuspoeng) 
– hvis vi rekker det, anbefales det sterkt å koble funksjonene til et enkelt JavaFX-baserte grensesnitt

---

## Prosjektrapport (PDF)
https://uisn-my.sharepoint.com/:f:/r/personal/266069_usn_no/Documents/OBJ2100%20Eksamen?csf=1&web=1&e=8Sl2KK
- Kort beskrivelse av hva hver person har gjort
- Teknologivalg og utfordringer
- Dokumentasjon av datasikkerhet og personvern (ca. ½–1 side)
- Navn og studentnummer eller en indifikator på deltagelse

---

## Testing
- Test hele applikasjonen som helhet
- Fjern `TestDB.java` før innlevering, men det er lurt å beholde den til siste test
- ZIP hele prosjektet og alle nødvendige ressurser før levering

---