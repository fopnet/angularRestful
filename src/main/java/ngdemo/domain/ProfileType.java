package ngdemo.domain;

/**
 * Created by Felipe on 28/06/2016.
 */
public enum ProfileType {

        PUBLIC("Public"),
        PUBLISHER("Publisher");

        String userProfileType;

        private ProfileType(String userProfileType){
            this.userProfileType = userProfileType;
        }

        public String ProfileType(){
            return userProfileType;
        }

        public static String getRoleName(Profile userProfile) {
            return "ROLE_"+ userProfile.getName().toUpperCase();
        }
}
