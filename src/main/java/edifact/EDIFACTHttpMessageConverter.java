package edifact;

import org.milyn.edi.unedifact.d01b.D01BInterchangeFactory;
import org.milyn.smooks.edi.unedifact.model.UNEdifactInterchange;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class EDIFACTHttpMessageConverter extends AbstractHttpMessageConverter<UNEdifactInterchange> {
    private D01BInterchangeFactory factory;

    public EDIFACTHttpMessageConverter() {
        //https://tools.ietf.org/html/rfc1767
        super(new MediaType("application", "EDIFACT"));
        try {
            this.factory = D01BInterchangeFactory.getInstance();
        } catch (IOException | SAXException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected UNEdifactInterchange readInternal(Class<? extends UNEdifactInterchange> clazz,
                                                HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return factory.fromUNEdifact(inputMessage.getBody());
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected void writeInternal(UNEdifactInterchange interchange,
                                 HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        factory.toUNEdifact(interchange, new OutputStreamWriter(outputMessage.getBody(), "UTF-8"));
    }
}