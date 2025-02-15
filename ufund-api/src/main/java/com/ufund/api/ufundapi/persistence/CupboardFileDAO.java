import org.springframework.stereotype.Component;

import com.ufund.api.ufundapi.model.Need;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class CupboardFileDAO implements CupboardDAO {
    private static final Logger LOG = Logger.getLogger(CupboardFileDAO.class.getName());

    @Override
    public Need[] getNeeds() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNeeds'");
    }

    @Override
    public Need[] findNeeds(String containsString) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findNeeds'");
    }

    @Override
    public Need getNeed(String name) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNeed'");
    }

    @Override
    public Need createNeed(String name, int cost, int quantity, String type) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createNeed'");
    }

    @Override
    public Need updateNeed(String name) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateNeed'");
    }

    @Override
    public boolean deleteNeed(String name) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteNeed'");
    }
}