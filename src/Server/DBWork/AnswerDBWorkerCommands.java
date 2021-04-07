package Server.DBWork;

public class AnswerDBWorkerCommands {
    private boolean successfulResult;
    private String phrase;

    public AnswerDBWorkerCommands(boolean successfulResult, String phrase){
        this.successfulResult = successfulResult;
        this.phrase = phrase;
    }

//    public void setPhrase(String phrase) {
//        this.phrase = phrase;
//    }
//
//    public void setSuccessfulResult(boolean successfulResult) {
//        this.successfulResult = successfulResult;
//    }

    public String getPhrase() {
        return phrase;
    }

    public boolean isSuccessfulResult() {
        return successfulResult;
    }
}
