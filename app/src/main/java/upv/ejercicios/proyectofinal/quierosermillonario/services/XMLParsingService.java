package upv.ejercicios.proyectofinal.quierosermillonario.services;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import upv.ejercicios.proyectofinal.quierosermillonario.constants.AppConstants;
import upv.ejercicios.proyectofinal.quierosermillonario.interfaces.ParsingInterface;
import upv.ejercicios.proyectofinal.quierosermillonario.model.QuestionItem;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.StringUtils;

/**
 * Created by migui on 0020.
 */


public class XMLParsingService implements ParsingInterface {

    private BufferedReader inputXMLFileReader;
    private XmlPullParser xmlPullParser;
    private Context context;

    // DONE: implement opening
    private void openQuestionsFile(String sysLang) throws IOException, XmlPullParserException{
        String xmlFileName ;
        String langSuffix;

        if (sysLang.toLowerCase().contains("es-"))
            langSuffix = "ES";
        else
            langSuffix = "EN";

        xmlFileName = AppConstants.QUESTIONS_FILE_NAME + langSuffix + ".xml";
        inputXMLFileReader = new BufferedReader(
                new InputStreamReader(this.getContext().openFileInput(xmlFileName))
        );
        xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
        xmlPullParser.setInput(inputXMLFileReader);
    }

    // DONE: implement file cursor(s) closure(s)...
    private void closeQuestionsFile() throws IOException {
        if (inputXMLFileReader != null)
            inputXMLFileReader.close();
    }

    @Override
    public List<QuestionItem> parseQuestionsFile(String sysLanguage) throws IOException, XmlPullParserException {
        if (this.getContext() != null) {
            List<QuestionItem> questionsList = new ArrayList<>();

            openQuestionsFile(sysLanguage);

            // DONE: do the parsing of questions with XMLPullParser Object
            if (inputXMLFileReader != null && xmlPullParser != null) {
                int currentEventType = xmlPullParser.getEventType();
                while (currentEventType != XmlPullParser.END_DOCUMENT) {
                    switch (currentEventType) {
                        case XmlPullParser.START_TAG:
                            if (xmlPullParser.getName()!=null
                                && xmlPullParser.getName().compareToIgnoreCase(AppConstants.XML_TAG_QUESTION) == 0) {


                                String questionNumber = xmlPullParser.getAttributeValue(null, AppConstants.XML_ATTR_QUESTION_NUMBER);
                                if (StringUtils.isEmpty(questionNumber))
                                    throw new XmlPullParserException("Error while parsing XML questions file. Question number attribute not found");
                                String text = xmlPullParser.getAttributeValue(null, AppConstants.XML_ATTR_QUESTION_TEXT);
                                if (StringUtils.isEmpty(text))
                                    throw new XmlPullParserException("Error while parsing XML questions file. Question text attribute not found");
                                String answer1 = xmlPullParser.getAttributeValue(null, AppConstants.XML_ATTR_ANSWER1);
                                String answer2 = xmlPullParser.getAttributeValue(null, AppConstants.XML_ATTR_ANSWER2);
                                String answer3 = xmlPullParser.getAttributeValue(null, AppConstants.XML_ATTR_ANSWER3);
                                String answer4 = xmlPullParser.getAttributeValue(null, AppConstants.XML_ATTR_ANSWER4);
                                if (StringUtils.isEmpty(answer1) || StringUtils.isEmpty(answer2) ||
                                        StringUtils.isEmpty(answer3) || StringUtils.isEmpty(answer4))
                                    throw new XmlPullParserException("Error while parsing XML questions file. Not all 4 possible answers populated");
                                String rightAnswer = xmlPullParser.getAttributeValue(null, AppConstants.XML_ATTR_RIGHT_ANSWER);
                                if (StringUtils.isEmpty(rightAnswer))
                                    throw new XmlPullParserException("Error while parsing XML questions file. Right answer not found for the question");
                                String audience = xmlPullParser.getAttributeValue(null, AppConstants.XML_ATTR_AUDIENCE_ANSWER);
                                if (StringUtils.isEmpty(audience))
                                    audience = "1";
                                String phone = xmlPullParser.getAttributeValue(null, AppConstants.XML_ATTR_PHONE_ANSWER);
                                if (StringUtils.isEmpty(phone))
                                    phone = "1";
                                String fifty1 = xmlPullParser.getAttributeValue(null, AppConstants.XML_ATTR_FIFTY1_ANSWER);
                                if (StringUtils.isEmpty(fifty1))
                                    fifty1 = "1";
                                String fifty2 = xmlPullParser.getAttributeValue(null, AppConstants.XML_ATTR_FIFTY2_ANSWER);
                                if (StringUtils.isEmpty(fifty1))
                                    fifty2 = String.valueOf(Integer.valueOf(fifty1) + 1);

                                QuestionItem question = new QuestionItem(questionNumber, text, answer1, answer2,
                                        answer3, answer4, rightAnswer, audience, phone, fifty1, fifty2);
                                questionsList.add(question);
                            }

                            break;


                        default:
                            break;
                    }
                    currentEventType = xmlPullParser.next();
                }
            }
            closeQuestionsFile();
            return questionsList;
        } else {
            return null;
        }
    }

    public XMLParsingService(Context context) {
        this.context = context;
    }


    public XMLParsingService() {
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
