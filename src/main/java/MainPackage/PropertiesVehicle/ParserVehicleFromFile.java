package MainPackage.PropertiesVehicle;

import MainPackage.core.annotations.Autowired;
import MainPackage.orm.entity.Types;
import MainPackage.orm.service.TypesService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParserVehicleFromFile {

    @Autowired
    private TypesService typesService;

    public List<VehicleType> loadVehicleTypeList() throws Exception {

        List<VehicleType> vehicleTypeList = new ArrayList<>();

        FileReader fis = new FileReader("C:\\Users\\Asus\\Desktop\\autopark\\src\\main\\resources\\types.csv");
        Scanner scanner = new Scanner(fis);

        while(scanner.hasNext()){
            String [] csv = scanner.nextLine().split(",");
            vehicleTypeList.add(new VehicleType(Integer.parseInt(csv[0]),csv[1],Double.parseDouble(csv[2])));
        }
        for(VehicleType v : vehicleTypeList){
           // typesService.getAll().stream().forEach(x -> System.out.println(x.getString()));
            typesService.save(new Types((long) typesService.getAll().get(typesService.getAll().size()-1).getTypeId()+1,v.getTypeName(),v.getTaxCoefficient()));
            System.out.println("Plane:" + typesService.getAll().get(typesService.getAll().size()-1).getTypeId()+1);
        }
        return vehicleTypeList;
    }


}
