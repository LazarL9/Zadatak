import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static List<Osoba> osobaList=new ArrayList<>();


    public static void ispis(List<Osoba> osobe,Integer razmak){
        String prazno="";
        for(int i=0;i<razmak;i++){
            prazno+="    ";
        }

        for (Osoba osoba : osobe) {
            System.out.println(prazno + osoba.getIme());
            if (osoba.getDjeca() != null) {
                ispis(osoba.getDjeca(), razmak + 1);
            }
        }
    }

    public static void provjeraGreske(Osoba osoba,String rootRoditelj,Integer rootRoditeljId){

        for (int i=0;i<osoba.getDjeca().size();i++){
            try {
                if(osoba.getDjeca().get(i).getIme().equals(rootRoditelj)){
                    throw new CyclicRelationshipException();
                }else {
                    provjeraGreske(osoba.getDjeca().get(i), rootRoditelj,rootRoditeljId);
                }
            }catch (CyclicRelationshipException e){
                System.out.println(e.getMessage());
                //System.out.println(osoba.getIme());
                //System.out.println(rootRoditeljId);
                //System.out.println(osobaList.get(rootRoditeljId).getDjeca().get(0));

                osobaList.set(rootRoditeljId,osobaList.get(rootRoditeljId).getDjeca().get(0));
            }
        }

    }

    public static void provjera() {
        prva:for(int i=0;i<osobaList.size();i++){
            for(int j=0;j<osobaList.get(i).getDjeca().size();j++){
                for(int x=0;x<osobaList.size();x++){
                    if(i>= osobaList.size()){
                        break prva;
                    }
                    if(osobaList.get(i).getDjeca().get(j).getIme().equals(osobaList.get(x).getIme())){

                        osobaList.get(i).getDjeca().set(j,osobaList.get(x));
                        osobaList.remove(x);

                        for(int q=0;q<osobaList.size();q++){
                            provjeraGreske(osobaList.get(q),osobaList.get(q).getIme(),q);
                            if(osobaList.get(q).getDjeca()!=null){
                                for(int z=0;z<osobaList.get(q).getDjeca().size();z++){

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void izradaListe(String[] polje){
        String roditeljIme=polje[1];
        String djeteIme=polje[0];
        Boolean postojiRoditelj=false;

        for (Osoba osoba : osobaList) {
            if (osoba.getIme().equals(roditeljIme)) {
                postojiRoditelj = true;
                Osoba djete = new Osoba(djeteIme, new ArrayList<>());

                List<Osoba> djeca = osoba.getDjeca();
                djeca.add(djete);

                osoba.setDjeca(djeca);
                break;
            }
        }

        for(int i=0;i< osobaList.size();i++){
            if(osobaList.get(i).getDjeca()!=null){
                for (int j=0;j< osobaList.get(i).getDjeca().size();j++){
                    if(osobaList.get(i).getDjeca().get(j).getIme().equals(roditeljIme)){
                        for(int x=0;x<osobaList.size();x++){
                            if(osobaList.get(x).getIme().equals(roditeljIme)){

                                List<Osoba> listaDjeceRoditelja= osobaList.get(i).getDjeca();

                                listaDjeceRoditelja.remove(j);
                                listaDjeceRoditelja.add(osobaList.get(x));
                                osobaList.remove(x);
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }


        if(!postojiRoditelj){
            Osoba djete=new Osoba(djeteIme,new ArrayList<>());

            List<Osoba> djeca=new ArrayList<>();
            djeca.add(djete);

            osobaList.add(new Osoba(roditeljIme,djeca));
        }
    }

    public static void main(String[] arg){
        List<String> imena=null;

        try {
            imena= Files.readAllLines(Paths.get("TextFiles/input.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> imenaList=new ArrayList<>();
        for(int i=0;i<imena.size();i++){
            imenaList.addAll(Arrays.asList(imena.get(i).split("\\s+")));
            izradaListe(imena.get(i).split("\\s+"));
        }
        provjera();
        ispis(osobaList,0);
    }
}

