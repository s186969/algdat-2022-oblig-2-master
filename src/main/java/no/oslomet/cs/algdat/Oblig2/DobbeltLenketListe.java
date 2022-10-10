package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.*;


public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    //Oppgave 1
    public DobbeltLenketListe(T[] a) {
        new DobbeltLenketListe(); //oppretter et objekt med konstruktøren
        Objects.requireNonNull(a,"Tabellen a er null!"); //sjekker om tabellen er null
        this.hode = new Node<>(null); //oppretter hale/hode som utgangspunkt for å bygge videre
        this.hale = hode;

        for(int i=0;i<a.length;i++) { //fylle på en node så lenge verdien i arrayet ikke er null, hopper over null-verdier
            if (a[i] != null) {
                hale.neste = new Node(a[i], hale, null);
                hale = hale.neste;
                antall++;
            }
        }
        if(antall == 0){ //hvis antallet noder fortsatt er 0 må hode = hale, som er null
            hode = hale;
        } else{
            hode = hode.neste; //hvis det er bygget på noder må vi sørge for at hode ikke lengre er null-verdien
            hode.forrige = null; //hode.forrige = null;
        }
    }
    //  Oppgave 3b)
    //  Underliggende metode kommer fra 1.2.3
    private static void fratilKontroll(int antall, int fra, int til) {
        if (fra < 0)
            throw new IndexOutOfBoundsException("fra(" + fra + ") er negativ!");

        if (til > antall)
            throw new IndexOutOfBoundsException("til(" + til + ") > antall(" + antall + ")");

        //  Obs! Her sier oppgaveteksten at det skal stå "IndexOutOfBoundsException", men testen feiler hvis
        //  det ikke står "IllegalArgumentException".
        if (fra > til)
            throw new IllegalArgumentException("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

    public Liste<T> subliste(int fra, int til) {
        fratilKontroll(antall, fra, til);

        DobbeltLenketListe<T> liste = new DobbeltLenketListe<>();  // ny liste
        Node<T> p = finnNode(fra);        // finner noden med indeks lik fra

        for (int i = fra; i < til; i++) {
            liste.leggInn(p.verdi);
            p = p.neste;
        }
        return liste;
    }
    //Oppgave 1
    @Override
    public int antall() {
        return antall;
    }
    //oppgave 1
    @Override
    public boolean tom() {
        if(antall == 0){
            return true;
        } else {
            return false;
        }
    }
    //Oppgave 2b)

    //Sjekkliste for boolean metoden leggInn(T verdi):
    //● Stoppes null-verdier? Kastes i så fall en NullPointerException?
    //● Blir det korrekt hvis listen fra før er tom?
    //● Blir det korrekt hvis listen fra før ikke er tom?
    //● Blir antallet økt?
    //● Blir endringer økt?
    //● Er det rett returverdi?
    @Override
    public boolean leggInn(T verdi) {
        // Null-verdier er ikke tillatt. Start derfor med en
        //sjekk (bruk en requireNonNull-metode fra klassen Objects)
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

        Node<T> n = new Node<>(verdi, hale, null);

        if (tom()){
            //listen på forhånd er tom
            hode = hale = n;
        }else{
            //listen er ikke tomt. Kun hale-pekeren som skal endres etter innleggingen.
            //Pass da på at forrige-peker og neste-peker i den nye noden og i den noden som opprinnelig
            //lå bakerst, får korrekte verdier.
            hale.neste = n;
            n.forrige= hale;
            hale = n;
        }

        //Husk at antallet må økes etter en innlegging. Det samme
        //med variabelen endringer. Metoden skal returnere true
        antall++;
        endringer++;
        return true;
        //throw new UnsupportedOperationException();
    }
    //Oppgave 5
    @Override
    public void leggInn(int indeks, T verdi) {
        //  Feilmelding hvis det er oppgitt nullverdier
        Objects.requireNonNull(verdi, "Det er ikke tillatt med null-verdier!");

        //  Ulovlig hvis oppgitt indeks er lik antall
        indeksKontroll(indeks, true);

        //1) listen er tom,
        if (tom()) {
            Node<T> p = new Node<T>(verdi);
            hode = hale = p;
            p.forrige = null;
            p.neste = null;

        }
        //2) verdien skal legges først,
        else if (indeks == 0) {
            Node<T> p = new Node<T>(verdi);
            p.forrige = null;
            p.neste = hode;
            hode.forrige = p;
            hode = p;
        }
        //3) verdien skal legges bakerst og
        else if (indeks == antall) {
            leggInn(verdi);
            antall--; //metoden "leggInn" øker antallet, blir dobbel økning i denne metoden hvis jeg ikke tar bort denne.
        }
        //4) verdien skal legges mellom to andre verdier. Sørg for at neste- og forrige-pekere får korrekte
        //verdier i alle noder. Spesielt skal forrige-peker i den første noden være null og neste-peker i
        //den siste noden være null.*/
        else {
            Node<T> q = finnNode(indeks);
            Node<T> p = new Node<T>(verdi);
            Node<T> r = finnNode(indeks - 1);
            p.neste = q;
            p.forrige = r;
            r.neste = p;
            q.forrige = p;
        }
        antall++;
        endringer++;
    }

    //Oppgave 4
    @Override
    public boolean inneholder(T verdi)
    {
        return indeksTil(verdi) != -1;
    }

    //  Oppgave 3a)
    //  Mye av oppgaven kommer fra 3.3.3
    private Node<T> finnNode(int indeks) {
        //  Hjelpevariabel
        Node<T> p;

        //  Leter etter node dersom indeksen er mindre enn antall / 2
        if (indeks < antall / 2) {
            p = hode;

            //Starter fra hode og øker mot høyre
            for (int i = 0; i < indeks; i++) {
                p = p.neste;
            }

        //  Leter etter node dersom indeksen er større enn antall / 2
        } else {
            p = hale;

            //  Starter fra hale og minker mot venstre
            for (int i = antall - 1; i > indeks; i--) {
                p = p.forrige;
            }
        }
        return p;
    }

    //  Tilhører oppgave 3a)
    //  Se Programkode 3.3.3 a). Desverre en helt lik kopi
    @Override
    public T hent(int indeks) {
        //  indeksKontroll sjekker om indeks er lovlig
        //  Når det er oppgitt false innebærer det at det ikke er tilatt at "indeks" er lik "antall"
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    //Oppgave 4
    @Override
    public int indeksTil(T verdi) {
/*        throw new UnsupportedOperationException();*/

        if (verdi == null) {
            return -1;
        }

        Node<T> q = hode;
        int i = 0;
        for (; i < antall; i++) {
            if (!verdi.equals(q.verdi)) {
                q = q.neste;
            } else {
                return i;
            }
        }
        return -1;
    }

    //  Tilhører oppgave 3a)
    //  Se Programkode 3.3.3 a). Mye lik som koden derfra og.
    @Override
    public T oppdater(int indeks, T nyverdi) {
        //  Feilmelding hvis det er oppgitt nullverdier
        Objects.requireNonNull(nyverdi, "Det er ikke tillatt med null-verdier. Prøv igjen.");

        //  Ulovlig hvis oppgitt indeks er lik antall
        indeksKontroll(indeks, false);

        Node<T> p = finnNode(indeks);
        //  Erstatter eksisterende verdi på indeks med verdien nyverdi.
        T gammelVerdi = p.verdi;
        p.verdi = nyverdi;

        //  Endringer økes med 1.
        endringer++;

        //  Den gamle verdien returneres.
        return gammelVerdi;
    }
    //Oppgave 6
    @Override
    public boolean fjern(T verdi) {
        //  Hvis "verdi" ikke er i listen, returnerer metoden false
        if (verdi == null) {
            return false;
        }

        //Hjelpevariabler
        Node <T> p = hode;

        while (p != null) {
            if (p.verdi.equals(verdi)) {
                break;
            }
            p = p.neste;
        }
        if (p == null) {
            return false;
        }

        //  Hvis metoden skal fjerne det første elementet
        if (verdi.equals(hode.verdi)) {

            //  Hvis listen har bare ett element
            if (hode.neste == null) {
                hode = null;                    //  Null-verdi
                hale = null;                    //  Null-verdi

            //  Hvis listen har mer enn ett element
            } else {
                hode = hode.neste;              //  Flytte hode til neste verdi
                hode.forrige = null;            //  Null-verdi
            }

        //  Hvis metoden skal fjerne det siste elementet
        } else if (verdi.equals(hale.verdi)) {
            hale = hale.forrige;                //  Flytte hale til forrige verdi
            hale.neste = null;                  //  Null-verdi

        //  Hvis metoden skal fjerne en verdi mellom det første og siste elementet
        } else {
            p.forrige.neste = p.neste;          //  Dirigere pekeren til neste verdi
            p.neste.forrige = p.forrige;        //  Dirigere pekeren til forrige verdi
        }
        endringer++;
        antall--;
        return true;
    }

    //  Oppgave 6
    //  Relevant kode befinner seg i 3.3.3 b)
    @Override
    public T fjern(int indeks) {
        //  Ulovlig hvis oppgitt indeks er lik antall
        indeksKontroll(indeks, false);

        //  Hjelpevariabler
        Node <T> p = finnNode(indeks);

        //  Hvis listen har bare ett element
        if (antall == 1) {
            hode = null;                        //  Null-verdi
            hale = null;                        //  Null-verdi

        //  Hvis det første elementet skal fjernes
        } else if (indeks == 0) {
            hode = hode.neste;                  //  Flytte hode til neste verdi
            hode.forrige = null;                //  Null-verdi

        //  Hvis det siste elementet skal fjernes
        } else if (p == hale) {
            hale = hale.forrige;                //  Flytte hale til forrige verdi
            hale.neste = null;                  //  Null-verdi

        //  Hvis et element mellom første og siste skal fjernes
        } else {
            p.forrige.neste = p.neste;          //  Dirigere pekeren til neste verdi
            p.neste.forrige = p.forrige;        //  Dirigere pekeren til forrige verdi
        }
        endringer++;
        antall--;
        return p.verdi;
    }

    //Oppgave 7
    //Nesten identisk kode som oppgave 2, Avsnitt 3.3.2.
//Metoden skal «tømme» listen og nulle alt slik at
//«søppeltømmeren» kan hente alt som ikke lenger brukes.
    @Override
    public void nullstill() {
        Node<T> p = hode;
        Node <T> q;
//Start i hode og gå mot hale ved hjelpe pekeren neste. For hver node «nulles»
//nodeverdien og alle nodens pekere.
        while (p != null)
        {
            q = p.neste;
            p.neste = null;
            p.verdi = null;
            p = q;
        }
        //Til slutt settes både hode og hale til null, antall til 0
        //og endringer økes.
        hode = hale = null;
        antall = 0;
        endringer ++;
        //throw new UnsupportedOperationException();
    }
//Nulstill 2. måte nulstill2.måte()? (Oppgave 7).
    //2. måte: Lag en løkke som inneholder metodekallet fjern(0) (den første noden fjernes) og
    //som går inntil listen er tom
public void nullstill2() {
        //fjerner noder hvis de finns
        while(antall != 0){
            fjern(0);
        }
}

//hjelpemetode til testing av nullstill metodene:
// generer random integer arrays.
public static Integer[] random(Integer[] a) {
    Random r = new Random();     // en randomgenerator
    for (int k = a.length - 1; k >= 0; k--){
        int i = r.nextInt(k + 1);  // tilfeldig tall fra [0,k]
        a[k]=i;
    }
    return a;
}

    //Oppgave 2 a)1.
    @Override
    public String toString() { //oppgave 2
        //i hjelpemetoden er antall definert som antall noder i listen.
        //hvis listen er tom, returnerer en tom liste.
        if (antall == 0) {
            return "[]";
        }else {
            //bruker stringBuilder får å bygge strengen som skal returneres
            StringBuilder s = new StringBuilder();
            s.append("[");
            //starter å append verdier i listen fra starten (hode) mot sluten for alle nodene som inneholder verdier
            for (Node<T> n = hode; n != null; n = n.neste) {
                s.append(n.verdi);
                //så langt listen inneholder verdier, verdiene skal append til strengen.
                if (n.neste != null) {
                    s.append(", ");
                }
            }
            s.append(']');
            return s.toString();
        }
        // throw new UnsupportedOperationException();
    }
    //Oppgave 2 a)2.
    public String omvendtString() {
        if (antall == 0){
            return "[]"; //i hjelpemetoden er antall definert som antall noder i listen.
        } else {
            //bruker stringBuilder får å bygge strengen som skal returneres
            StringBuilder s = new StringBuilder();
            s.append("[");
            //starter å append verdier i listen fra slutten (hale) mot startern for alle nodene som inneholder verdier
            for (Node<T> n = hale; n != null; n = n.forrige) {
                s.append(n.verdi);
                //så langt listen inneholder verdier, verdiene skal append til strengen.
                if (n.forrige != null) {
                    s.append(", ");
                }
            }
            s.append(']');
            return s.toString();
        }
        // throw new UnsupportedOperationException();
    }

    //Oppgave 8 b)
    @Override
    public Iterator<T> iterator() {
        return new DobbeltLenketListeIterator();
    }

    //Oppgave 8d)
    public Iterator<T> iterator(int indeks) {
        indeksKontroll(indeks, false);
        return new DobbeltLenketListeIterator(indeks);
    }

    //Metoden boolean  hasNext() og konstruktøren public  DobbeltLenketListeIterator() i
    //klassen DobbeltLenketListeIterator er ferdigkodet og skal ikke endres.
    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            //Programkode 3.3.4 a) ++
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        // Oppgave 8c)
        private DobbeltLenketListeIterator(int indeks) {
            denne = finnNode(indeks);
            fjernOK = false;
            iteratorendringer = endringer;
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        // Oppgave 8a)
        @Override
        public T next()
        {
            // Programkode 3.2.5e)
            if (iteratorendringer != endringer) {
                throw new ConcurrentModificationException("Listen er endret!");
            }

            // Programkode 3.3.4c)
            if (!hasNext()) throw new NoSuchElementException("Ingen verdier!");

            fjernOK = true; // nå kan remove() kalles
            T denneVerdi = denne.verdi; // tar vare på verdien i denne
            denne = denne.neste; // flytter denne til den neste noden
            return denneVerdi; // returnerer verdien
            
        }
        //Opggave 9
        @Override
        public void remove() {
            if(!fjernOK){
                throw new IllegalStateException("Kan ikke fjerne fra en tom liste!");
            }
            if(endringer != iteratorendringer){
                throw new ConcurrentModificationException("Endringer er ulik iteratorendringer");
            }
            fjernOK = false;
            if(antall == 1){
                hode=hale=null;
            }
            else if(denne == null){
                hale = hale.forrige;
                hale.neste = null;
            }
            else if(denne.forrige == hode){
                hode = denne.forrige.neste;
                hode.forrige= null;
            } else{
                Node p = denne.forrige;
                p.forrige.neste = p.neste;
                p.neste.forrige = p.forrige;
            }
            antall--;
            endringer++;
            iteratorendringer++;
        }

    } // class DobbeltLenketListeIterator

// Oppgave 10
    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {

        // kode basert på oppgave 4 i 4.2.4
        //finner antall noder i listen
        int n = liste.antall();
        //så langt listen har noder, skal while løkken kjøres.
        while (n > 0){
            //lager en itarator som itererer gjennom lista
            Iterator<T> iterator = liste.iterator();
            // setter en kandidat for minst vedri
            T min = iterator.next();
            int index = 0;//start index
            for (int i = 1; i < n; i++){
                T verdi = iterator.next();
                //sammenligner verdien som ble satt som minst verdi med neste verdi
                //hvis verdi er mindre enn min (kandidaten til minst verdi),
                // settes indeks lik i og min verdien oppdateres
                if (c.compare(verdi, min) < 0){
                    index = i;
                    min = verdi; // fant en som var mindre
                }
            }
            // kaller på leggInn metoden fra oppgave 2b) og legges i listen samme
            //verdi som er returnert av fjern()metoden i oppgave 6.
            liste.leggInn(liste.fjern(index));
            //teller ned fram til det finns ingen flere noder slik at while løkken stopper
            n--;
        }
    }
} // class DobbeltLenketListe
