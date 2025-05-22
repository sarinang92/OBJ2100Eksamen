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
.
├── db/
│   └── kino_database_init.sql           
├── kode/
│   └── src/main/java/kino/
│       ├── config/                       
│       ├── controller/                   
│       ├── dao/                          
│       ├── model/                        
│       ├── repository/                   
│       ├── service/                      
│       ├── view/                         
│       ├── Main.java                     
│       └── TestDB.java                   
├── systemvedlikehold_ressurser/
│   ├── config.properties                 
│   ├── slettinger.dat                   
│   └── slettinger_backup.dat            
├── pom.xml                              
├── README.md    

## Kjøring
Start `Main.java`. Sørg for at `config.properties` inneholder riktig sti til `pg_dump.exe` for backup-funksjonen.

## Database
Importer SQL-filen i `db/kino_database_init.sql`.  
Kjør deretter følgende rettigheter i pgAdmin:

```sql
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA kino TO "Case";
GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA kino TO "Case";
```

## Testbrukere for innlogging:

tomas = planlegger, passord: 1234

samot = betjent, passord: 4321

Case = administrator, passord: Esac

## Systemvedlikehold 

For å teste funksjoner knyttet til systemvedlikehold (eksport av slettelogger, sikkerhetskopi av databasen og rydding av ubetalte billetter), kan man kjøre `TestDB.java`
Logger eksporteres til `systemvedlikehold_ressurser/exported_logs/slettinger_backup.dat`
Backup lagres i `systemvedlikehold_ressurser/db_backups/`
Sletting av ubetalte billetter skjer direkte i databasen.


## Dokumentasjon
For mer informasjon, se full prosjektrapport i `rapport.pdf`.