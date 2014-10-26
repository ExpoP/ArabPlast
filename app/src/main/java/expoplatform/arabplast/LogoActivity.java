package expoplatform.arabplast;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LogoActivity extends Activity {

    private final String tag = getClass().getSimpleName();
    private Dao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        //Instance for working with the database
        dao = new Dao(getApplicationContext());

        getExpocategories();
        getExhibitors();
        dao.populateExibitorsTable();


        try {
            Thread.sleep(3L * 1000L);
        } catch (InterruptedException e) {
            Log.e(tag, "Main thread interrupted", e);
        }

        startActivity(new Intent(this, Exhibitions.class));
    }

    private void getExpocategories() {
        String queryExpocategories = "select id, name from business_areas";
        new QueryRemoteExpocategoriesDbTask().execute(queryExpocategories);
    }

    private void getExhibitors() {
        String queryExhibitors = "SELECT " +
                "exhibitors.id, " +                                      //1 integer
                "exhibitors.name, " +                                    //2
                "countries.name, " +                                     //3
                "cities.name, " +                                        //4
                "exhibitors.address, " +                                 //5
                "exhibitors.website, " +                                 //6
                "exhibitors.about, " +                                   //7
                "exhibitors.business_area_id, " +                        //8 integer
                "exhibitors.email, " +                                   //9
                "exhibitors.phone " +                                    //10
                "FROM " +
                "exhibitors, countries, cities " +
                "WHERE " +
                "exhibitors.country_id=countries.id " +
                "AND " +
                "exhibitors.city_id=cities.id;";// +
//                "AND " +
//                "exhibitors.business_area_id=business_areas.id;";

        new QueryRemoteExhibitorssDbTask().execute(queryExhibitors);
    }


    class QueryRemoteExpocategoriesDbTask extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... params) {
            Connection connection = null;
            Statement statement = null;
            ResultSet result = null;
            String query = params[0];

            try {
                Class.forName("org.postgresql.Driver");
                String desktopIPandPortForGenymotionEmulator = "10.0.3.2:5432";
                String url = "jdbc:postgresql://" + desktopIPandPortForGenymotionEmulator + "/expoplatform?user=expo&password=hQuvtlKjTWCR3uh1";
                Log.i(tag, "url: " + url);

                connection = DriverManager.getConnection(url);
                statement = connection.createStatement();

                Log.i(tag, "query: " + query);
                result = statement.executeQuery(query);

                /*int resultSize = 0;
                while (result.next()) {
                    resultSize++;
                }

                Log.i(tag, "resultSize: " + resultSize);*/

                dao.populateExpoCategoriesTable(result);

            } catch (ClassNotFoundException e) {
                Log.e(tag, "The PostgreSQL driver was not found", e);
            } catch (SQLException e) {
                Log.e(tag, "Bad query or something is wrong with a remote database", e);
            } finally {
                try {
                    if (result != null) {
                        result.close();
                    }
                } catch (SQLException e) {
                    Log.e(tag, "Error closing ResultSet", e);
                }
                try {
                    if (statement != null) {
                        statement.close();
                    }
                } catch (SQLException e) {
                    Log.e(tag, "Error closing Statement");
                }
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    Log.e(tag, "Error closing connection", e);
                }
            }

            return null;
        }
    }

    class QueryRemoteExhibitorssDbTask extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... params) {
            Connection connection = null;
            Statement statement = null;
            ResultSet result = null;
            String query = params[0];

            try {
                Class.forName("org.postgresql.Driver");
                String desktopIPandPortForEmulator = "10.0.3.2:5432";
                String url = "jdbc:postgresql://" + desktopIPandPortForEmulator + "/expoplatform?user=expo&password=hQuvtlKjTWCR3uh1";
                Log.i(tag, "url: " + url);

                connection = DriverManager.getConnection(url);
                statement = connection.createStatement();

                Log.i(tag, "query: " + query);
                result = statement.executeQuery(query);

                /*int resultSize = 0;
                while (result.next()) {
                    resultSize++;
                }

                Log.i(tag, "resultSize: " + resultSize);*/

                dao.populateRawExhibitorsTable(result);


                    /*while (result.next()) {
                        String retval1 = result.getString(1);
                        String retval2 = result.getString(2);
                        String retval3 = result.getString(3);
                        String retval4 = result.getString(4);
                        String retval5 = result.getString(5);
                        String retval6 = result.getString(6);
                        String retval7 = result.getString(7);
                        String retval8 = result.getString(8);
                        String retval9 = result.getString(9);
                        String retval10 = result.getString(10);
                        Log.i(tag, retval1 + " " + retval2 + " " + retval3 + " " + retval4 + " " + retval5 +
                                " " + retval6 + " " + retval7 + " " + retval8 + " " + retval9 + ' ' + retval10);
                        Log.i(tag, retval2);
                    }*/

            } catch (ClassNotFoundException e) {
                Log.e(tag, "The PostgreSQL driver was not found", e);
            } catch (SQLException e) {
                Log.e(tag, "Bad query or something is wrong with a remote database", e);
            } finally {
                try {
                    if (result != null) {
                        result.close();
                    }
                } catch (SQLException e) {
                    Log.e(tag, "Error closing ResultSet", e);
                }
                try {
                    if (statement != null) {
                        statement.close();
                    }
                } catch (SQLException e) {
                    Log.e(tag, "Error closing Statement");
                }
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    Log.e(tag, "Error closing connection", e);
                }
            }

            return null;
        }
    }




    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
