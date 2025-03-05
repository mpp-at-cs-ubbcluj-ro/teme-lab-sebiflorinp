import model.ComputerRepairRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ComputerRepairRequestTest {
    @Test
    @DisplayName("First test")
    public void firstTest() {
        ComputerRepairRequest computerRepairRequest = new ComputerRepairRequest();
        assertEquals(0, computerRepairRequest.getID());
        assertEquals("", computerRepairRequest.getOwnerName());
    }
    
    @Test
    @DisplayName("Second test")
    public void secondTest() {
        ComputerRepairRequest computerRepairRequest = new ComputerRepairRequest(1, "Ion", "Str. Munci", "07123445678", "Asus", "2/2/2002", "Nu merge");
        
        assertEquals(1, computerRepairRequest.getID());
        assertEquals("Ion", computerRepairRequest.getOwnerName());
    }
      
}
