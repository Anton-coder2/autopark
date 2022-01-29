package MainPackage.threads;

import MainPackage.checkLocation.VehicleCollection;
import MainPackage.checkLocation.Workroom;
import MainPackage.core.annotations.Autowired;
import MainPackage.threads.anntotations.Schedule;

import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Collect {

    @Autowired
    private Workroom workroom;
    @Autowired
    private VehicleCollection collection;

    @Schedule(delta = 1_000)
    public Set<Integer> collect() throws Exception {

        workroom.newDetectBreaking();
        return workroom.checkAllVehicle(collection);

    }
}
