package implementation;

import database.model.*;
import database.service.KeywordService;
import database.service.PublicationService;
import main.Parameters;
import service.SuggestingService;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SuggestingServiceImpl implements SuggestingService {

    @Override
    public List<Publication> suggestPublicationsByKeyword(String requestKeyword) {
        PublicationService publicationService = new PublicationService();
        KeywordService keywordService = new KeywordService();
        Keyword keyword = keywordService.findByKeyword(requestKeyword);
        List<Publication> resultList = publicationService.findAllPublications()
                .stream()
                .filter(publication -> publication.getKeywords().contains(keyword))
                .collect(Collectors.toList());

        return resultList;
    }

    @Override
    public List<String> executeSuggestionQueryByRating(String keyword, Cluster cluster, int limit) {
        String url = "jdbc:postgresql://localhost:5432/postgres_sts";
        String user = Parameters.postgresLogin;
        String password = Parameters.postgresPassword;
        List<String> result = new LinkedList<>();
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery( "select metric || '  ' || name || '  ' || link from \n" +
                    "(select  distinct p.name, p.year, p.metric, p.link \n" +
                    "\tfrom science_theme_searcher.publication p,\n" +
                    "\t\tscience_theme_searcher.keyword k,\n" +
                    "\t\tscience_theme_searcher.keywordtopublication kp,\n" +
                    "\t\tscience_theme_searcher.authortopublication ap,\n" +
                    "\t\tscience_theme_searcher.clustertoauthor ca\n" +
                    "\twhere k.keyword = '"+keyword+"'\n" +
                    "\t\tand k.id = kp.id_keyword\n" +
                    "\t\tand kp.id_publication = p.id\n" +
                    "\t\tand p.id = ap.id_publication\n" +
                    "\t\tand ap.id_author = ca.id_author\n" +
                    "\t\tand ca.id_cluster = "+cluster.getId()+"\n" +
                    "\torder by metric desc\n" +
                    "\tlimit "+limit+")sel");

            while (rs.next()) {
                result.add(rs.getString(1));
            }
            return result;

        } catch (Exception ex) {
            //System.out.println(ex.getStackTrace());
        }
        return null;
    }


    @Override
    public List<String> executeSuggestionQueryByYear(String keyword, Cluster cluster, int limit) {
        String url = "jdbc:postgresql://localhost:5432/postgres_sts";
        String user = Parameters.postgresLogin;
        String password = Parameters.postgresPassword;
        List<String> result = new LinkedList<>();
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select year || '  ' || name || '  ' || link from \n" +
                    "(select  distinct p.name, p.year, p.metric, p.link \n" +
                    "\tfrom science_theme_searcher.publication p,\n" +
                    "\t\tscience_theme_searcher.keyword k,\n" +
                    "\t\tscience_theme_searcher.keywordtopublication kp,\n" +
                    "\t\tscience_theme_searcher.authortopublication ap,\n" +
                    "\t\tscience_theme_searcher.clustertoauthor ca\n" +
                    "\twhere k.keyword = '"+keyword+"'\n" +
                    "\t\tand k.id = kp.id_keyword\n" +
                    "\t\tand kp.id_publication = p.id\n" +
                    "\t\tand p.id = ap.id_publication\n" +
                    "\t\tand ap.id_author = ca.id_author\n" +
                    "\t\tand ca.id_cluster = "+cluster.getId()+"\n" +
                    "\torder by year desc\n" +
                    "\tlimit "+limit+")sel");

            while (rs.next()) {
                result.add(rs.getString(1));
            }
            return result;

        } catch (Exception ex) {
            //System.out.println(ex.getStackTrace());
        }
        return null;
    }

    @Override
    public String findClustersAffiliation(Cluster cluster) {
        Set<Author> authorSet = cluster.getAuthors();
        HashMap<String, Integer> affiliationsMap = new HashMap<>();
        int max = 0;
        String resultAffiliation = "";
        for (Author author: authorSet) {
            for (Affiliation affiliation: author.getAffiliations()) {
                int nextCount = affiliationsMap.getOrDefault(affiliation.getName(), 0)+1;
                if (max < nextCount) {
                    max = nextCount;
                    resultAffiliation = affiliation.getName();
                }
                affiliationsMap.put(affiliation.getName(), nextCount);
            }
        }
        return resultAffiliation;
    }
}
