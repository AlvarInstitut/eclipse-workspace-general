package generated;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import generated.Oferta.Vehiculo;
import generated.Oferta.Vehiculo.Modelo;

public class JAXBExempleCotxesGuardar {
    public static void main(String[] args) throws FileNotFoundException {

     try {

        File file = new File("cotxes.xml");
        File file2 = new File("cotxes2.xml");
        FileOutputStream os = new FileOutputStream(file2);
        JAXBContext jaxbContext = JAXBContext.newInstance(Oferta.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Oferta oferta = (Oferta) jaxbUnmarshaller.unmarshal(file);
        
        Vehiculo v = oferta.getVehiculo().get(0);
        v.setMarca("Seat");
        Modelo m = new Modelo();
        m.setValue("Ibiza");
        m.setColor("blau");
        v.setModelo(m);
        v.setMatricula("7777AAA");
        
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.marshal(oferta, os);

      } catch (JAXBException e) {
        e.printStackTrace();
      }

    }
}
