import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;

/* Each table you wish to access in your database requires a model class, like this example: */
public class Doughnut
{
    /* First, map each of the fields (columns) in your table to some public variables. */
    public int DoughnutId;
    public String Name;
    public int ShapeId;
    public String MainFlavour;
    public String Filling;
    public String Topping;

    /* Next, prepare a constructor that takes each of the fields as arguements. */
    public Doughnut(int DoughnutId, String Name, int ShapeId, String MainFlavour, String Filling, String Topping)
    {
        this.DoughnutId = DoughnutId;
        this.Name = Name;
        this.ShapeId = ShapeId;
        this.MainFlavour = MainFlavour;
        this.Filling = Filling;
        this.Topping = Topping;
    }

    /* A toString method is vital so that your model items can be sensibly displayed as text. */
    @Override public String toString()
    {
        return Name;
    }

    /* Different models will require different read and write methods. Here is an example 'loadAll' method 
     * which is passed the target list object to populate. */
    public static void readAll(List<Doughnut> list)
    {
        list.clear();       // Clear the target list first.

        /* Create a new prepared statement object with the desired SQL query. */
        PreparedStatement statement = Application.database.newStatement("SELECT DoughnutId, Name, ShapeId, MainFlavour, Filling, Topping FROM Doughnuts"); 

        if (statement != null)      // Assuming the statement correctly initated...
        {
            ResultSet results = Application.database.runQuery(statement);       // ...run the query!

            if (results != null)        // If some results are returned from the query...
            {
                try {								// ...add each one to the list.
                    while (results.next()) {        			                           
                        list.add( new Doughnut(results.getInt("DoughnutId"), 
                                results.getString("Name"), 
                                results.getInt("ShapeID"),
                                results.getString("MainFlavour"),
                                results.getString("Filling"),
                                results.getString("Topping")) );
                    }
                }
                catch (SQLException resultsexception)       // Catch any error processing the results.
                {
                    System.out.println("Database result processing error: " + resultsexception.getMessage());
                }
            }
        }

    }

}
