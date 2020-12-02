package classesGeoHib;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;


public class AccesAmbListForEach {

    public static void main(String[] args) {

        Session sessio = SessionFactoryUtil.getSessionFactory().openSession();
        Query q = sessio.createQuery("from Comarca");
        
        ObjectContainer bd = Db4oEmbedded.openFile("Geo.db4o");

        for (Comarca com : (List<Comarca>) q.list()) {
        	classesGeo.Comarca comNova = new classesGeo.Comarca(com.getNomC(),com.getProvincia(),null);
        	ArrayList<classesGeo.Poblacio> llPoblNova = new ArrayList<classesGeo.Poblacio>();
        	
        	for (Poblacio p : com.getPoblacios()) {
        		classesGeo.Poblacio poblNova = null;
        		if (p.getAltura()==null) 
        			poblNova = new classesGeo.Poblacio(p.getCodM(),comNova,p.getNom(),p.getPoblacio(),p.getExtensio().floatValue(),null,p.getLongitud(),p.getLatitud(),p.getLlengua(),null);
        		else
        			 poblNova = new classesGeo.Poblacio(p.getCodM(),comNova,p.getNom(),p.getPoblacio(),p.getExtensio().floatValue(),p.getAltura().intValue(),p.getLongitud(),p.getLatitud(),p.getLlengua(),null);
        			
        		ArrayList<classesGeo.Institut> llInsNova = new ArrayList<classesGeo.Institut>();
        		
        		for (Institut i : p.getInstituts()) {
        			classesGeo.Institut insNova = new classesGeo.Institut(i.getCodi(),poblNova,i.getNom(),i.getAdreca(),i.getNumero(),i.getCodpostal());
        			llInsNova.add(insNova);
        		}
        		poblNova.setInstituts(llInsNova);
        		llPoblNova.add(poblNova);
        	}
        	comNova.setPoblacions(llPoblNova);
        	bd.store(comNova);
            System.out.println(comNova.getNomC() + " - " + comNova.getProvincia());
        }
        bd.close();
        sessio.close();
 
    }
}