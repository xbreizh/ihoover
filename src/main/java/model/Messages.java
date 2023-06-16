package model;

public enum Messages {

    GENERAL_RULES (
            "Vous pilotez un aspirateur automatique. \n" +
            "Vous allez devoir péciser:\n" +
            "- la dimension de la pièce\n" +
            "- la position de départ de l'aspirateur\n" +
            "- les instructions de déplacement\n" +
            "Vous pouvez réafficher ce message à tout moment en tapant \"H\""),
    ROOM_SIZE_INSTRUCTION (
            "Veuillez entrer les dimensions de la pièce au format longueur X largeur\n" +
            "La valeur doit être comprise entre 1 et 999999999\n" +
            "Exemple: 23X44\n\n" +
            "Veuillez entrer les dimensions de la pièce: \n"),
    INITIAL_POSITION_INSTRUCTION (
            "Veuillez entrer les coordonnées de la position initiale de l'aspirateur.\n" +
            "Celle-ci se compose de:" +
            "- model.Position en longueur (entier compris entre 1 et 999999999)\n" +
            "- model.Position en largeur (entier compris entre 1 et 999999999)\n" +
            "- model.Orientation (N (Nord), E (Est), W (Ouest), S (Sud))\n" +
            "Chaque valeur sera séparée par une virgule\n" +
            "Note: les coordonnées devront bien être valides par rapport aux dimensions de la pièce fournis: \n" +
            "Exemple: 3,5,N\n" +
            "Veuillez entrer les coordonnées de la position initiale: "),
    MOVE_INSTRUCTION (
            "Quelles instructions souhaitez vous donner?\n" +
            "Voici la liste des possibilités: A (Avancer), G (Gauche), D (Droite)\n" +
            "Tape X pour arrêter"),
    FINISH (
            "######################\n" +
            "####### THE END ######\n" +
            "######################\n");

    private final String value;

    Messages(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
