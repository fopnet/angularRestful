package ngdemo.domain;

/**
 * Created by Felipe on 28/06/2016.
 */
public enum ProfileType {

        PUBLIC("Public",2L),
        PUBLISHER("Publisher",1L);

        String name;
        Long id;

        private ProfileType(String name, Long id){
            this.name = name;
            this.id = id;
        }

        public String ProfileType(){
            return name;
        }

        public String getName() {
            return name;
        }

        public Long getId() {
            return id;
        }

        public static String getRoleName(Profile userProfile) {
            return "ROLE_"+ userProfile.getName().toUpperCase();
        }

        public static ProfileType getById(Long id) {
            return PUBLIC.id.equals(id) ? PUBLIC : PUBLISHER;
        }

        static public ProfileType getByName(String name) {
            for (ProfileType type :ProfileType.values()) {
                if (type.getName().equalsIgnoreCase(name))
                    return type;
            }
            return null;
        }

        public Profile toProfile() {
            return new Profile(getId(), getName());
        }

        @Override
        public String toString() {
            return name;
        }



}
