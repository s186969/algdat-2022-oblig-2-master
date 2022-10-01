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
        //throw new UnsupportedOperationException();
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    //Oppgave 1
    /*● Stoppes en null-tabell? Kastes i så fall en NullPointerException?
            ● Blir det korrekt hvis parametertabellen inneholder en eller flere null-verdier?
            ● Blir det korrekt hvis parametertabellen er tom (har lengde 0)?
            ● Blir det korrekt hvis parametertabellen kun har null-verdier?
            ● Blir det korrekt hvis parametertabellen har kun én verdi som ikke er null?
            ● Blir antallet satt korrekt?
            ● Får verdiene i listen samme rekkefølge som i tabellen?*/
    public DobbeltLenketListe(T[] a) { //skal håndtere tomme lister
        Objects.requireNonNull(a,"Tabellen a er null!");
        // Sjekk om det er en tom liste og kast feilmld?
        this.hode = null;
        this.hale = null;
        Node current;
        for(int i=0;i<a.length;i++) {
            if (a[i] != null) {
                current = new Node(a[i]);
                hode = current;
                this.antall += 1;
                break;
            }
        }

        Node tmp = null;
        current = hode;
        for(int i=1;i<a.length;i++){
            if (a[i] != null) {
                current.neste = new Node(a[i]);
                current.forrige = tmp;
                tmp = current;
                current = current.neste;
                hale = current;
                this.antall += 1;
            }
        }
    }

    public Liste<T> subliste(int fra, int til) {
        throw new UnsupportedOperationException();
    }

    //Oppgave 1
    @Override
    public int antall() {
        //throw new UnsupportedOperationException();
        return antall;
    }
    //oppgave 1
    @Override
    public boolean tom() {
        //throw new UnsupportedOperationException();
        if(hode == hale){
            return true;
        } else {
            return false;
        }
    }

    //Oppgave 2b)
    @Override
    public boolean leggInn(T verdi) {

        throw new UnsupportedOperationException();
    }

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

        //  Erstatter eksisterende verdi på indeks med verdien nyverdi.
        //  Den gamle verdien returneres.
        Node<T> p = finnNode(indeks);
        T gammelVerdi = p.verdi;

        p.verdi = nyverdi;
        return gammelVerdi;
    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
    }
    //Oppgave 2 a)
    @Override
    public String toString() { //oppgave 2
        //i hjelpemetoden er antall definert som antall noder i listen.
        //hvis listen er tom, returnerer en tom liste.
        if (antall == 0) return "[]";

        StringBuilder s = new StringBuilder();
        s.append("[");
        //starter å append verdier i listen fra starten (hode) mot sluten for alle nodene som inneholder verdier
            for (Node<T> p = hode; p != null; p = p.neste) {
                s.append(p.verdi).append(", ");
            }
        s.append(']');
        return s.toString();
       // throw new UnsupportedOperationException();
    }

    public String omvendtString() {
        if (antall == 0) return "[]"; //i hjelpemetoden er antall definert som antall noder i listen.

        StringBuilder s = new StringBuilder().append("[");
            for (Node<T> p = hale; p != null; p = p.forrige) {
                s.append(p.verdi).append(", ");
            }
        s.append(']');

        return s.toString();
       // throw new UnsupportedOperationException();
    }

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
        String[] s = {"Ole", null, "Per", "Kari", null};
        Liste<String> liste = new DobbeltLenketListe<>(s);
        System.out.println(liste.antall() + " " + liste.tom());
    }

} // class DobbeltLenketListe
