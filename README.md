# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende studenter:
* Kristin Hauso, a362060, s362060@oslomet.no
* Alex Vu, s186969, s186969@oslomet.no
* Lelia Marcela Marcau, s358978, s358978@oslomet.no
* Knut Andreas Grove, s358979, s358979@oslomet.no

# Arbeidsfordeling

I oppgaven har vi hatt følgende arbeidsfordeling:
* Kristin har hatt hovedansvar for oppgave...
* Alex har hatt hovedansvar for oppgave 3 og 6
* Lelia har hatt hovedansvar for oppgave... 
* Knut har hatt hovedansvar for oppgave...

# Oppgavebeskrivelse

<h3>Oppgave 1</h3>
...

<h3>Oppgave 2</h3>
...

<h3>Oppgave 3</h3>
<p>Besvarelsen vil begynne med å gjøre rede for oppgave 3a).</p>

<p>I finnNode(int indeks) starter metoden med å lete etter en node ved hjelp av en if-setning. Dersom 
«indeks» er mindre enn («antall» / 2), vil metoden gå gjennom en for-løkke som øker «hode» til neste 
verdi. Dersom «indeks» er større enn («antall» / 2), for-løkken starte fra «hale» og minske til forrige 
verdi.
</p>

<p>I hent(int indeks) har besvarelsen hentet fra kompendiet i programkode 3.3.3.a). Denne metoden 
kontrollerer først om hvorvidt «indeks» er gyldig. Ettersom det er oppgitt false, innebærer det at 
det ikke er gyldig å oppgi «indeks» skal være lik «antall». Tilslutt returnerer den verdien som kommer 
fra finnNode(int indeks).
</p>

<p>I oppdater(int  indeks,  T  nyverdi) begynner metoden med å sjekke om det er oppgitt null-verdier som 
ikke er tillatt. Det er også ikke tillatt å oppgi en verdi i «indeks» som er lik «antall». Deretter 
erstatter metoden verdien på «indeks» med «nyverdi» ved hjelp av hjelpevariabel. Tilslutt returnerer 
metoden den opprinnelige verdien.'
</p>

<p>Herunder vil besvarelsen gjøre rede for oppgave 3b).</p>

<p>Besvarelsen har hentet metoden fratilKontroll() fra kompendiet i 1.2.3  «Feil og unntak». Denne metoden 
sørger for at tabellintervallet a[fra:til> (fra og med «fra», til men ikke med «til») er lovlig. Det gjøres 
oppmerksom på at oppgaveteksten skriver at at ArrayIndexOutOfBoundsException skal byttes med 
IndexOutOfBoundsException. I følge testen som var vedlagt i den obligatoriske oppgaven, ville testen 
ikke godkjenne IndexOutOfBoundsException i den siste if-setningen. Besvarelsen har derfor valgt å følge 
testen som ønsket å bytte ArrayIndexOutOfBoundsException med IllegalArgumentException.
</p>

<p>I subliste(int  fra,  int  til) starter metoden å bruke fratilKontroll() for å «indeks» fra og til er 
lovlige. Deretter lager metoden en liste ved hjelp av en for-løkke som tar inn verdiene i finnNode(fra).
Tilslutt returnerer metoden listen.
</p>

<h3>Oppgave 4</h3>
...

<h3>Oppgave 5</h3>
...

<h3>Oppgave 6</h3>
...

<h3>Oppgave 7</h3>
...

<h3>Oppgave 8</h3>
...

<h3>Oppgave 9</h3>
...

<h3>Oppgave 10</h3>
...
