# Kinoapplikasjon – Eksamensprosjekt OBJ2100 (Vår 2025)

## Om prosjektet
Dette er en kommandolinje-basert Java-applikasjon for et kinosystem, utviklet som eksamensprosjekt i faget Objektorientert programmering 2 ved USN Ringerike.

Applikasjonen støtter ulike roller og funksjoner:
- **Kunde**: Bestille billetter
- **Kinobetjent**: Registrere betaling og rydde ubetalte billetter
- **Planlegger**: Administrere filmer, visninger, statistikk
- **Administrator**: Brukeradministrasjon og systemvedlikehold

## Teknologi og struktur
- **Java 21**
- **PostgreSQL**
- **Maven**
- Bygget med pakke-struktur: `controller/`, `service/`, `repository/`, `model/`, `view/`

## Prosjektstruktur

├── src/
│ └── main/java/kino/...
├── db/
│ └── kino_database_init.sql
├── systemvedlikehold_ressurser/
│ ├── config.properties
│ └── slettinger.dat
├── exported_logs/
│ └── (genereres automatisk)
├── README.md
└── rapport.pdf

## Kjøring
Start `Main.java`. Sørg for at `config.properties` inneholder riktig sti til `pg_dump.exe` for backup-funksjonen.

## Database
Importer SQL-filen i `db/kino_database_init.sql`.  
Kjør deretter følgende rettigheter i pgAdmin:

```sql
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA kino TO "Case";
GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA kino TO "Case";
```

## Dokumentasjon
For mer informasjon, se full prosjektrapport i `rapport.pdf`.