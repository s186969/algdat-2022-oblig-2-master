package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;


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

        if (fra > til)
            throw new IndexOutOfBoundsException("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
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
        throw new UnsupportedOperationException();
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
            for (int i = antall - 1; i < indeks; i--) {
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
    public int indeksTil(T verdi){
        throw new UnsupportedOperationException();

/*        for (int i = 0; i < antall; i++)
        {
            if (a[i].equals(verdi)) return i;
        }
        return -1;*/
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
        throw new UnsupportedOperationException();
    }

    //Oppgave 6
    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException();
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
//nodeverdien og alle nodens pekere. Til slutt settes både hode og hale til null, antall til 0
//og endringer økes.
        while (p != null)
        {
            q = p.neste;
            p.neste = null;
            p.verdi = null;
            p = q;
        }

        hode = hale = null;
        antall = 0;
        endringer ++;
        //throw new UnsupportedOperationException();
    }
//Nulstill 2. måte nulstill2.måte()? (Oppgave 7).
    //2. måte: Lag en løkke som inneholder metodekallet fjern(0) (den første noden fjernes) og
    //som går inntil listen er tom

    //Oppgave 2 a)1.
    @Override
    public String toString() { //oppgave 2
        //i hjelpemetoden er antall definert som antall noder i listen.
        //hvis listen er tom, returnerer en tom liste.
        if (antall == 0) {
            return "[]";
        }else {
            StringBuilder s = new StringBuilder();
            s.append("[");
            //starter å append verdier i listen fra starten (hode) mot sluten for alle nodene som inneholder verdier
            for (Node<T> n = hode; n != null; n = n.neste) {
                s.append(n.verdi);
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
            StringBuilder s = new StringBuilder();
            s.append("[");
            for (Node<T> n = hale; n != null; n = n.forrige) {
                s.append(n.verdi);
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
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args){
        //Oppgave 1
        //Liste<String> liste = new DobbeltLenketListe<>();
        //System.out.println(liste.antall() + " " + liste.tom());
        System.out.println();
        // String[] s = {"Ole", null, "Per", "Kari", null};
        // Liste<String> liste = new DobbeltLenketListe<>(s);
        // System.out.println(liste.antall() + " " + liste.tom());

        //*********Oppgave 2**************
        System.out.println("*********Oppgave 2**************");
        System.out.println("Testing oppgave 2 a)");
        String[] s1 = {}, s2 = {"A"}, s3 = {null,"A",null,"B",null}, s4 = {null, null, null};
        DobbeltLenketListe<String> l1 = new DobbeltLenketListe<>(s1);
        DobbeltLenketListe<String> l2 = new DobbeltLenketListe<>(s2);
        DobbeltLenketListe<String> l3 = new DobbeltLenketListe<>(s3);
        DobbeltLenketListe<String> l4 = new DobbeltLenketListe<>(s4);
        System.out.println(l1.toString() + " " + l2.toString()
                + " " + l3.toString() + " "+ l4.toString() + " " + l1.omvendtString() + " "
                + l2.omvendtString() + " " + l3.omvendtString() + " " + l4.omvendtString());
        // Utskrift: [] [A] [A, B] [] [A] [B, A]
        System.out.println("Testing oppgave 2b)");
        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>();
        System.out.println(liste.toString() + " " + liste.omvendtString());
        for (int i = 1; i <= 3; i++) {
            liste.leggInn(i);
            System.out.println(liste.toString() + " " + liste.omvendtString());
        }
        // Utskrift:
        // [] []
        // [1] [1]
        // [1, 2] [2, 1]
        // [1, 2, 3] [3, 2, 1]
        System.out.println("***********OPPGAVE 2 SLUTTER HER***********");
        //***********OPPGAVE 2 SLUTTER HER***********

    }

} // class DobbeltLenketListe
