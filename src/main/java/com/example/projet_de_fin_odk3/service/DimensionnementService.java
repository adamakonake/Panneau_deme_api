package com.example.projet_de_fin_odk3.service;

import com.example.projet_de_fin_odk3.exception.AlreadyExistException;
import com.example.projet_de_fin_odk3.exception.NotFoundException;
import com.example.projet_de_fin_odk3.model.*;
import com.example.projet_de_fin_odk3.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class DimensionnementService {

    @Autowired
    DimensionnementRepository dimensionnementRepository;
    @Autowired
    EquipementRepository equipementRepository;
    @Autowired
    AppareilRepository appareilRepository;
    @Autowired
    ListEquiRepository listEquiRepository;
    @Autowired
    ListAppRepository listAppRepository;
    @Autowired
    ElectricienRepository electricienRepository;
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    UtilisateurRepository utilisateurRepository;

    //Fonction pour enregistrer un dimensionnement
    public Dimensionnement createDimensionnement(String description,List<Appareil> appareilList, Map<String,Double> coordonnee,int idUser) throws Exception {

        if (dimensionnementRepository.findByDescription(description) != null)
            throw new AlreadyExistException("exist");

        Dimensionnement dimensionnement = new Dimensionnement();
        dimensionnement.setDescription(description);
        verifAppareils(appareilList);
        Map<String,Object> infoDim = calculeDim(appareilList);
        System.out.println("-------------------------------------");
        //Map<String,Object> dimResult = new HashMap<>();
        int puissanceOnduleur = (int) infoDim.get("puissanceOnduleur");
        int puissanceRegulateur = (int) infoDim.get("puissanceRegulateur");
        int tensionTravail = (int) infoDim.get("tensionTravail");
        Equipement panneau = (Equipement) infoDim.get("panneau");
        Equipement onduleur = equipementRepository.findFirstByPuissanceIsGreaterThanEqualAndTypeEquipementTitreOrderByPuissanceAsc(puissanceOnduleur,"Onduleur hybride");
        Equipement regulateur = equipementRepository.findFirstByPuissanceIsGreaterThanEqualAndTensionEqualsAndTypeEquipementTitreOrderByPuissanceAsc(puissanceRegulateur,tensionTravail,"Régulateur");

        Utilisateur utilisateur = utilisateurRepository.findByIdUtilisateur(idUser);
        dimensionnement.setDate(LocalDate.now());
        dimensionnement.setPuissanceCrete((int) infoDim.get("puissanceCrete"));
        dimensionnement.setCapaciteBatterie((int) infoDim.get("capaciteBatterie"));
        dimensionnement.setPuissanceRegulateur((int) infoDim.get("puissanceCrete"));
        dimensionnement.setPuissanceOnduleur(puissanceOnduleur);
        dimensionnement.setTensionTravail(tensionTravail);
        dimensionnement.setElectricien(addElectricienToDim(coordonnee));
        dimensionnement.setUtilisateur(utilisateur);
        Dimensionnement newDimensionnement = dimensionnementRepository.save(dimensionnement);

        if (onduleur == null)
            throw new Exception("erreur server");
        /*List<Equipement> listOnduleur = equipementRepository.findByPuissanceAndTypeEquipementTitre(onduleur.getPuissance(),"Onduleur hybride");
        List<Equipement> listPanneau = equipementRepository.findByPuissanceAndTypeEquipementTitre(panneau.getPuissance(), "Panneau");
        List<Equipement> listRegulateur = equipementRepository.findByPuissanceAndTensionAndTypeEquipementTitre(regulateur.getPuissance(),tensionTravail,"Régulateur");*/
        List<Equipement> listBatterie = equipementRepository.findByTensionAndIntensiteAndTypeEquipementTitre(12,100,"Batterie");

        for (Appareil appareil : appareilList){
            ListApp listApp = new ListApp();
            listApp.setDimensionnement(newDimensionnement);
            listApp.setAppareil(appareil);
            listAppRepository.save(listApp);
        }

        ListEqui listEquiPanneau = new ListEqui();
        listEquiPanneau.setDimensionnement(newDimensionnement);
        listEquiPanneau.setEquipement(panneau);
        listEquiPanneau.setQuantite((int) infoDim.get("nombrePanneau"));
        listEquiRepository.save(listEquiPanneau);

        ListEqui listEquiBatterie = new ListEqui();
        listEquiBatterie.setDimensionnement(newDimensionnement);
        listEquiBatterie.setEquipement(listBatterie.get(0));
        listEquiBatterie.setQuantite((int) infoDim.get("nombreBatterie"));
        listEquiRepository.save(listEquiBatterie);

        ListEqui listEquiRegulateur = new ListEqui();
        listEquiRegulateur.setDimensionnement(newDimensionnement);
        listEquiRegulateur.setEquipement(regulateur);
        listEquiRegulateur.setQuantite((int) infoDim.get("nombreRegulateur"));
        listEquiRepository.save(listEquiRegulateur);

        ListEqui listEquiOnduleur = new ListEqui();
        listEquiOnduleur.setDimensionnement(newDimensionnement);
        listEquiOnduleur.setEquipement(onduleur);
        listEquiOnduleur.setQuantite(1);
        listEquiRepository.save(listEquiOnduleur);

        /*Map<String,Object> panneauInfo = new HashMap<>();
        panneauInfo.put("panneau",panneau);
        panneauInfo.put("number",infoDim.get("nombrePanneau"));

        Map<String,Object> batterieInfo = new HashMap<>();
        batterieInfo.put("batterie",listBatterie.get(0));
        batterieInfo.put("number",infoDim.get("nombreBatterie"));

        Map<String,Object> regulateurInfo = new HashMap<>();
        regulateurInfo.put("regulateur",regulateur);
        regulateurInfo.put("number",infoDim.get("nombreRegulateur"));

        Map<String,Object> onduleurInfo = new HashMap<>();
        onduleurInfo.put("onduleur",onduleur);
        onduleurInfo.put("number",1);

        dimResult.put("dimensionnement",newDimensionnement);
        dimResult.put("panneaux",panneauInfo);
        dimResult.put("onduleurs",onduleurInfo);
        dimResult.put("batteries",batterieInfo);
        dimResult.put("regulateurs",regulateurInfo);
        dimResult.put("puissanceCrete",infoDim.get("puissanceCrete"));
        dimResult.put("capaciteBatterie",infoDim.get("capaciteBatterie"));
        //dimResult.put("listPanneau",listPanneau);
        dimResult.put("listBatterie",listBatterie);
        //dimResult.put("listRegulateur",listRegulateur);
        //dimResult.put("listOnduleurs",listOnduleur);*/

        return newDimensionnement;

    }

    //Fonction pour recuperer la liste des dimensionnemnets
    public List<Dimensionnement> allDImensionnementByUser(int idUser){
        List<Dimensionnement> list = dimensionnementRepository.findByUtilisateurIdUtilisateurOrderByDateDesc(idUser);
        if (list.isEmpty())
            throw new NotFoundException("empty");

        return list;
    }

    //Fonction pour recuperer les informations d'un dimensionnement
    public Map<String,Object> getInfoByDimensionnement(int idDim){
        Dimensionnement dimensionnement = dimensionnementRepository.findByIdDimensionnement(idDim);
        if (dimensionnement == null)
            throw new NotFoundException("invalid");

        Map<String,Object> dimResult = new HashMap<>();

        List<Appareil> appareilList = new ArrayList<>();
        List<ListApp> listApps = listAppRepository.findByDimensionnementIdDimensionnement(dimensionnement.getIdDimensionnement());
        Note note = noteRepository.findByDimensionnementIdDimensionnement(idDim);

        for (ListApp listApp : listApps){
            appareilList.add(listApp.getAppareil());
        }

        NotePojo notePojo = new NotePojo();
        if(note == null){
            notePojo.setValeur(0.0);
        }else {
            notePojo.setValeur(note.getValeur());
            notePojo.setIdNotePojo(note.getIdNote());
        }
        notePojo.setIdDim(idDim);
        notePojo.setIdUser(dimensionnement.getUtilisateur().getIdUtilisateur());
        notePojo.setIdElec(dimensionnement.getElectricien().getIdElectricien());

        Map<String,Object> panneauInfo = new HashMap<>();
        Map<String,Object> batterieInfo = new HashMap<>();
        Map<String,Object> regulateurInfo = new HashMap<>();
        Map<String,Object> onduleurInfo = new HashMap<>();

        List<ListEqui> listEquis = listEquiRepository.findByDimensionnementIdDimensionnement(dimensionnement.getIdDimensionnement());

        for(ListEqui listEqui : listEquis){
            if (listEqui.getEquipement().getTypeEquipement().getTitre().equals("Panneau")){
                panneauInfo.put("panneau",listEqui.getEquipement());
                panneauInfo.put("number",listEqui.getQuantite());
            }
            if (listEqui.getEquipement().getTypeEquipement().getTitre().equals("Batterie")){
                batterieInfo.put("batterie",listEqui.getEquipement());
                batterieInfo.put("number",listEqui.getQuantite());
            }
            if (listEqui.getEquipement().getTypeEquipement().getTitre().equals("Régulateur")){
                regulateurInfo.put("regulateur",listEqui.getEquipement());
                regulateurInfo.put("number",listEqui.getQuantite());
            }
            if (listEqui.getEquipement().getTypeEquipement().getTitre().equals("Onduleur hybride")){
                onduleurInfo.put("onduleur",listEqui.getEquipement());
                onduleurInfo.put("number",listEqui.getQuantite());
            }
        }

        List<Equipement> listPanneau = equipementRepository.findByPuissanceAndTypeEquipementTitre(((Equipement) panneauInfo.get("panneau")).getPuissance(), "Panneau");
        List<Equipement> listBatterie = equipementRepository.findByTensionAndIntensiteAndTypeEquipementTitre(12,100,"Batterie");
        List<Equipement> listRegulateur = equipementRepository.findByPuissanceAndTensionAndTypeEquipementTitre(((Equipement) regulateurInfo.get("regulateur")).getPuissance(),dimensionnement.getTensionTravail(),"Régulateur");
        List<Equipement> listOnduleur = equipementRepository.findByPuissanceAndTypeEquipementTitre(((Equipement) onduleurInfo.get("onduleur")).getPuissance(),"Onduleur hybride");

        dimResult.put("notePojo",notePojo);
        dimResult.put("panneauInfo",panneauInfo);
        dimResult.put("onduleurInfo",onduleurInfo);
        dimResult.put("batterieInfo",batterieInfo);
        dimResult.put("regulateurInfo",regulateurInfo);

        dimResult.put("listAppareil",appareilList);
        dimResult.put("listPanneau",listPanneau);
        dimResult.put("listBatterie",listBatterie);
        dimResult.put("listRegulateur",listRegulateur);
        dimResult.put("listOnduleurs",listOnduleur);

        return dimResult;

    }

    //Fonction pour verifier les appareils
    private void verifAppareils(List<Appareil> appareils){
        for (Appareil appareil : appareils){
            if (appareilRepository.findByIdAppareil(appareil.getIdAppareil()) == null)
                throw new NotFoundException("invalid");
        }
    }

    //Fonction calcule dimensionnement
    private Map<String,Object> calculeDim(List<Appareil> appareilList){
        double puissanceTotal = 0; //puissance totale
        double energieConsomme = 0; //energie consomme
        double energieProduite = 0; //energie à produire
        int tensionTravail = 0; //tension de travail
        for(Appareil appareil : appareilList){
            puissanceTotal = puissanceTotal + appareil.getPuissance();
            energieConsomme = energieConsomme + (appareil.getPuissance()*appareil.getHeureConso()* appareil.getQuantite());
        }

        energieProduite = energieConsomme + ((energieConsomme * 25)/100);
        if (puissanceTotal <= 1000) {
            tensionTravail = 12;
        } else if (puissanceTotal > 1000 && puissanceTotal <= 2000) {
            tensionTravail = 24;
        } else {
            tensionTravail = 48;
        }

        double capaciteTotalBatterie = (energieProduite*1)/(0.8*tensionTravail); // capacite total de la batterie = energie produite * nbre de jour autonomie / profondeur de decharge * tension de travail
        int nmbreSerieBatterie = (capaciteTotalBatterie/100) > ((int) (capaciteTotalBatterie/100)) ? ((int) (capaciteTotalBatterie/100))+1 : ((int) (capaciteTotalBatterie/100)); //nombre de serie(rangé) de batterie = capacite total de batterie / intensité du batterie choisis
        int nombreBatterie = nmbreSerieBatterie * (tensionTravail/12); // nombre  de batterie capacite/intensiteBatterieChoisi * tensionTtravail/tensionBatterieChoise

        double epEnKw = energieProduite/1000;
        double IRRADIATION_SOLAIRE = 5.78;
        double puissanceCretePanneau = ((epEnKw * 1000)/IRRADIATION_SOLAIRE); //puissance crete du panneau solaire

        Equipement panneau = equipementRepository.findFirstByPuissanceIsLessThanEqualAndTypeEquipementTitreOrderByPuissanceDesc((int)puissanceCretePanneau,"Panneau");
        if (panneau == null)
            panneau = equipementRepository.findFirstByPuissanceIsGreaterThanEqualAndTypeEquipementTitreOrderByPuissanceAsc((int)puissanceCretePanneau,"Panneau");
        double nbrePanneau = puissanceCretePanneau / panneau.getPuissance();
        int nombrePanneau = 0;

        if (puissanceCretePanneau<panneau.getPuissance())
            nombrePanneau = 1;
        else
            nombrePanneau = (((int) (nbrePanneau))%2 == 0) ? ((int)nbrePanneau)+2 : ((int)nbrePanneau)+1;

        int puissanceRegulateur = ((int) (puissanceCretePanneau))/nmbreSerieBatterie;

        System.out.println(nmbreSerieBatterie);
        System.out.println(puissanceCretePanneau/nmbreSerieBatterie);
        System.out.println(puissanceCretePanneau);

        int puissanceOndulaire = (int) ((puissanceCretePanneau*98)/100); //puissance minimale

        Map<String,Object> infoDim = new HashMap<>();
        infoDim.put("panneau",panneau);
        infoDim.put("nombrePanneau",nombrePanneau);
        infoDim.put("nombreBatterie",nombreBatterie);
        infoDim.put("nombreRegulateur",nmbreSerieBatterie);
        infoDim.put("puissanceRegulateur",puissanceRegulateur);
        infoDim.put("puissanceOnduleur",puissanceOndulaire);
        infoDim.put("tensionTravail",tensionTravail);
        infoDim.put("puissanceCrete",((int) puissanceCretePanneau));
        infoDim.put("capaciteBatterie",((int) capaciteTotalBatterie));

        return infoDim;
    }

    private Electricien addElectricienToDim(Map<String,Double> coordUser){

        List<Electricien> electricienList = electricienRepository.findAll();
        List<ElectricienDistancePojo> distancePojoList = new ArrayList<>();

        for (Electricien electricien : electricienList){
            final double rayonDeLaTerre = 6371.07103;
            double radiusLatUser = (coordUser.get("latitude")) * (Math.PI/180);
            double radiusLatMosq = electricien.getLatitude() * (Math.PI/180);
            double latitudeDiff = radiusLatMosq - radiusLatUser;
            double longitudeDiff = (electricien.getLongitude()-(coordUser.get("longitude")))*(Math.PI/180);
            double distance = 2 * rayonDeLaTerre * Math.sin(Math.sqrt(Math.sin(latitudeDiff/2) * Math.sin(latitudeDiff/2) + Math.cos(radiusLatUser) * Math.cos(radiusLatMosq) * Math.sin(longitudeDiff/2) * Math.sin(longitudeDiff/2)));
            ElectricienDistancePojo electricienDistancePojo = new ElectricienDistancePojo();
            electricienDistancePojo.setDistance(distance+"");
            electricienDistancePojo.setElectricien(electricien);
            distancePojoList.add(electricienDistancePojo);
        }

        System.out.println(distancePojoList);

        Collections.sort(distancePojoList);
        System.out.println("-------------------------------------1");

        System.out.println(distancePojoList);

        return distancePojoList.get(0).getElectricien();
    }




}
