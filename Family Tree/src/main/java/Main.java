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

    public static void krozCijeluListu(List<Osoba> osobaList1){
        for(int i=0;i<osobaList1.size();i++){
            testiranje(osobaList1,osobaList1.get(i).getIme(),i);
            if(osobaList1.get(i).getDjeca()!=null){
                for(int j=0;j<osobaList.get(i).getDjeca().size();j++){
                    krozCijeluListu(osobaList1.get(i).getDjeca());
                }
            }
        }
    }

    public static void testiranje(List<Osoba> osobe,String rootRoditelj,Integer rootRoditeljId){
        for(int i=0;i<osobe.size();i++){
            if(rootRoditeljId!=i){
                    if(osobe.get(i).getIme().equals(rootRoditelj)){
                        throw new CyclicRelationshipException();
                    }else if(osobaList.get(i).getDjeca()!=null){
                        testiranje(osobe.get(i).getDjeca(),rootRoditelj,-1);
                    }
            }
        }
    }

    public static void provjeraGreske(Osoba osoba,String rootRoditelj){

        for (int i=0;i<osoba.getDjeca().size();i++){
            if(osoba.getDjeca()!=null){
                for (int j=0;j<osoba.getDjeca().size();j++){
                    provjeraGreske(osoba.getDjeca().get(j),osoba.getDjeca().get(j).getIme());
                }
            }
            //try {
                if(osoba.getDjeca().get(i).getIme().equals(rootRoditelj)){
                    throw new CyclicRelationshipException();
                }else {
                    provjeraGreske(osoba.getDjeca().get(i), rootRoditelj);
                }
            //}catch (CyclicRelationshipException e){
                //System.out.println(e.getMessage());
                //System.out.println(osoba.getIme());
                //System.out.println(rootRoditeljId);
                //System.out.println(osobaList.get(rootRoditeljId).getDjeca().get(0));

                //osobaList.set(rootRoditeljId,osobaList.get(rootRoditeljId).getDjeca().get(0));
            //}
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
                            provjeraGreske(osobaList.get(q),osobaList.get(q).getIme());
                        }
                    }
                }
            }
        }
    }

    public static void izradaListe(String[] polje){
        String roditeljIme=polje[1];
        String djeteIme=polje[0];
        boolean postojiRoditelj=false;

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
        krozCijeluListu(osobaList);

        ispis(osobaList,0);
    }
}

