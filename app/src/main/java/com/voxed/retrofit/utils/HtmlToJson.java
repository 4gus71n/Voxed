package com.voxed.retrofit.utils;

import android.text.TextUtils;

import com.voxed.retrofit.responses.ApiVoxDetailCommentResponse;
import com.voxed.retrofit.responses.ApiVoxDetailResponse;
import com.voxed.retrofit.responses.ApiVoxResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.functions.Func1;

/**
 * Created by Agustin Tomas Larghi on 8/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class HtmlToJson {

    public static Func1<? super Response<ResponseBody>, ? extends Response<List<ApiVoxResponse>>> sVoxesMapper
            = new Func1<Response<ResponseBody>, Response<List<ApiVoxResponse>>>() {
        @Override
        public Response<List<ApiVoxResponse>> call(Response<ResponseBody> responseBody) {
            List<ApiVoxResponse> responseList = new ArrayList<>();

            try {
                if (responseBody.isSuccessful()) {
                    String htmlString = responseBody.body().string();
                    Document document = Jsoup.parse(htmlString);

                    for (Element voxNode : document.getElementsByClass("vox")) {
                        String id = voxNode.attr("data-hash");
                        if (!TextUtils.isEmpty(id)) {
                            String category = voxNode.getElementsByClass("category").get(0).text();
                            String comments = voxNode.getElementsByClass("comments").get(0).text();
                            String image = voxNode.getElementsByTag("img").get(0).attr("data-src");
                            String title = voxNode.getElementsByTag("h3").get(0).text();

                            ApiVoxResponse response = new ApiVoxResponse();

                            response.id = id;
                            response.title = title;
                            response.category = category;
                            response.comments = Integer.valueOf(comments);
                            response.image = image;

                            responseList.add(response);
                        }
                    }
                }
            } catch (Exception e) {
                return Response.error(responseBody.code(), responseBody.errorBody());
            } finally {
                return Response.success(responseList);
            }
        }
    };

    public static Func1<? super Response<ResponseBody>, ? extends Response<ApiVoxDetailResponse>> sVoxMapper
            = new Func1<Response<ResponseBody>, Response<ApiVoxDetailResponse>>() {
        @Override
        public Response<ApiVoxDetailResponse> call(Response<ResponseBody> responseBody) {
            ApiVoxDetailResponse apiVoxDetailResponse = new ApiVoxDetailResponse();

            try {
                if (responseBody.isSuccessful()) {
                    String htmlString = responseBody.body().string();
                    Document document = Jsoup.parse(htmlString);

                    String title = document.getElementsByAttributeValue("itemprop", "name").text();
                    String image = document.getElementsByAttributeValue("itemprop", "image").attr("src");
                    String opPostHtml = document.getElementsByAttributeValue("itemprop", "articleBody").html();

                    apiVoxDetailResponse.title = title;
                    apiVoxDetailResponse.image = image;
                    apiVoxDetailResponse.description = opPostHtml;

                    apiVoxDetailResponse.comments = new ArrayList<>();

                    for (Element commentNode : document.getElementsByClass("comment")) {

                        ApiVoxDetailCommentResponse apiVoxDetailCommentResponse = new ApiVoxDetailCommentResponse();

                        String commentId = commentNode.attr("id");
                        String timeStamp = commentNode.getElementsByClass("created_at").attr("data-timestamp");

                        apiVoxDetailCommentResponse.commentId = commentId;
                        apiVoxDetailCommentResponse.timeStamp = Long.valueOf(timeStamp);
                        apiVoxDetailCommentResponse.references = new ArrayList<String>();

                        Element commentDetailNode = commentNode.getElementById("content_" + commentId);

                        if (!commentDetailNode.getElementsByClass("youtubePrev").isEmpty()) {
                            Elements youtubeNode = commentDetailNode.getElementsByClass("youtubePrev");
                            String youtubeVideoId = youtubeNode.get(0).attr("data-video");
                            apiVoxDetailCommentResponse.youtubeid = youtubeVideoId;
                            youtubeNode.remove();
                        }

                        Elements commentDetailNodeRefs = commentDetailNode.getElementsByTag("a");
                        for (Element commentDetailNodeRef : commentDetailNodeRefs) {
                            if (commentDetailNodeRef.hasAttr("data-quote")) {
                                String commentRefId = commentDetailNodeRef.attr("data-quote");
                                apiVoxDetailCommentResponse.references.add(commentRefId);

                                commentDetailNodeRef.remove();
                            }
                            if (commentDetailNodeRef.hasAttr("class") && commentDetailNodeRef.attr("class").equals("attach_link")) {
                                apiVoxDetailCommentResponse.attachment = commentDetailNodeRef.attr("href");
                                commentDetailNodeRef.remove();
                            }
                        }

                        apiVoxDetailCommentResponse.body = commentDetailNode.html();

                        apiVoxDetailResponse.comments.add(apiVoxDetailCommentResponse);
                    }
                }
            } catch (Exception e) {
                return Response.error(responseBody.code(), responseBody.errorBody());
            } finally {
                return Response.success(apiVoxDetailResponse);
            }
        }
    };
}
