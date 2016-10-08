package datacomlink;

import org.milyn.edi.unedifact.d01b.ORDERS.Orders;
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactInterchange41;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class JAXMServlet {//implements javax.xml.messaging.OnewayListener

    @Async
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/9999US_AS2_20150715190948")
    public void onMessageReceived(@RequestBody UNEdifactInterchange41 amplop) {


        amplop.getMessages().stream().forEach(surat -> {
            // Membaca surat...
            System.out.println("\tNama topik: " + surat.getMessageHeader().getMessageIdentifier().getId());
            Orders pesananPembelian = (Orders) surat.getMessage();
            System.out.println("\tNama mitra " + pesananPembelian.getSegmentGroup2().get(0).getNameAndAddress().getPartyName().getPartyName1());
            System.out.println("\tJumlah SKU " + pesananPembelian.getSegmentGroup28().size());
            System.out.println("\tDeskripsi SKU " + pesananPembelian.getSegmentGroup28().get(0).getItemDescription().get(0).getItemDescription().getItemDescription1());
            System.out.println("\tJumlah " + pesananPembelian.getSegmentGroup28().get(0).getQuantity().get(0).getQuantityDetails().getQuantity());
        });
    }


}
