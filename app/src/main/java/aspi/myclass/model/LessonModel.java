package aspi.myclass.model;


public class LessonModel {
    public String lessonName, startTime, endTime, id, education, classNumber, parentId
            , lessonCode, description ,dayOfWeek;

    public boolean APP;

    public LessonModel() {
    }



//lessonName, startTime, endTime, id, education, classNumber, parentId, lessonCode, description, dayOfWeek
    public LessonModel(String lessonName, String startTime, String endTime, String id, String education, String classNumber, String parentId, String lessonCode, String description, String dayOfWeek) {
        this.lessonName = lessonName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.id = id;
        this.education = education;
        this.classNumber = classNumber;
        this.parentId = parentId;
        this.lessonCode = lessonCode;
        this.description = description.isEmpty()?"":description;
        this.dayOfWeek = dayOfWeek;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setLessonCode(String lessonCode) {
        this.lessonCode = lessonCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
