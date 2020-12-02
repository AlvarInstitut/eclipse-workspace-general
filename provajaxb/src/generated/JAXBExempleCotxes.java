package generated;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import generated.Oferta.Vehiculo;

public class JAXBExempleCotxes {
    public static void main(String[] args) {

     try {

        File file = new File("cotxes.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(Oferta.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Oferta oferta = (Oferta) jaxbUnmarshaller.unmarshal(file);
        for (Vehiculo v : oferta.getVehiculo() )
            System.out.println( v.getMatricula() + " (" + v.getMarca() + ")");

      } catch (JAXBException e) {
        e.printStackTrace();
      }

    }
}