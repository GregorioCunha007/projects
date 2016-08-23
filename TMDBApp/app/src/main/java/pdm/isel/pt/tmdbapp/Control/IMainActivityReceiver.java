package pdm.isel.pt.tmdbapp.Control;

import pdm.isel.pt.tmdbapp.Model.RequestedOrder;

/**
 * Created by Pedro on 08/12/2015.
 */
public interface IMainActivityReceiver {

    public boolean serviceReceivedToMainActivity(RequestedOrder rOrder);
}
