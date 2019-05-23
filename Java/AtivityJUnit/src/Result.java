
package activityjunit;

/**
 *
 * @author Administrator
 */
public enum Result {
    VICTORY("Vitória"),
    LOSE("Derrota"),
    DRAW("Empate");
    
    private final String description;
    
    private Result(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
    
    
}
