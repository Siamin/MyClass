package aspi.myclass.model.databaseModels;

public class LessonDatabaseModel {

    private String id = "id";
    private String lessonName = "ndars";
    private String startTime = "time";
    private String ensTime = "Minute";
    private String dayOfWeek = "dey";
    private String education = "location";
    private String description = "txt";
    private String lessonCode = "Characteristic";
    private String parentId = "did";
    private String classNumber = "class";


    public String getId() {
        return id;
    }

    public String getLessonName() {
        return lessonName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEnsTime() {
        return ensTime;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getEducation() {
        return education;
    }

    public String getDescription() {
        return description;
    }

    public String getLessonCode() {
        return lessonCode;
    }

    public String getParentId() {
        return parentId;
    }

    public String getClassNumber() {
        return classNumber;
    }
}
