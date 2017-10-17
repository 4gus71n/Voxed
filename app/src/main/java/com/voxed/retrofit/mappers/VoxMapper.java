package com.voxed.retrofit.mappers;

import com.voxed.model.Vox;
import com.voxed.model.VoxDetail;
import com.voxed.model.VoxMessage;
import com.voxed.retrofit.responses.ApiVoxDetailCommentResponse;
import com.voxed.retrofit.responses.ApiVoxDetailResponse;
import com.voxed.retrofit.responses.ApiVoxResponse;
import com.voxed.utils.rx.ObservableUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agustin Tomas Larghi on 8/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class VoxMapper {


    public static ObservableUtils.Transformable<List<ApiVoxResponse>, List<Vox>> sCollection =
            new ObservableUtils.Transformable<List<ApiVoxResponse>, List<Vox>>() {
                @Override
                public List<Vox> transformServerToModel(List<ApiVoxResponse> serverResponseWrapper) {
                    List<Vox> voxList = new ArrayList<>();

                    for (ApiVoxResponse apiVoxResponse : serverResponseWrapper) {
                        Vox vox = new Vox();
                        vox.setId(apiVoxResponse.id);
                        vox.setCategory(apiVoxResponse.category);
                        vox.setCommentCount(apiVoxResponse.comments);
                        vox.setImage(apiVoxResponse.image);
                        vox.setTitle(apiVoxResponse.title);
                        voxList.add(vox);
                    }

                    return voxList;
                }
            };

    public static ObservableUtils.Transformable<ApiVoxDetailResponse, VoxDetail> sSingle =
            new ObservableUtils.Transformable<ApiVoxDetailResponse, VoxDetail>() {
                @Override
                public VoxDetail transformServerToModel(ApiVoxDetailResponse serverResponseWrapper) {
                    VoxDetail voxDetail = new VoxDetail();

                    voxDetail.setTitle(serverResponseWrapper.title);
                    voxDetail.setDescription(serverResponseWrapper.description);
                    voxDetail.setImageUrl(serverResponseWrapper.image);

                    List<VoxMessage> messages = new ArrayList<>();

                    for (ApiVoxDetailCommentResponse apiVoxDetailCommentResponse : serverResponseWrapper.comments) {
                        VoxMessage voxMessage = new VoxMessage();
                        voxMessage.setBody(apiVoxDetailCommentResponse.body);
                        voxMessage.setCommentId(apiVoxDetailCommentResponse.commentId);
                        voxMessage.setAttachment(apiVoxDetailCommentResponse.attachment);
                        voxMessage.setYoutubeId(apiVoxDetailCommentResponse.youtubeid);
                        voxMessage.setReferences(apiVoxDetailCommentResponse.references);
                        voxMessage.setTimeStamp(apiVoxDetailCommentResponse.timeStamp);
                        messages.add(voxMessage);
                    }

                    voxDetail.setMessages(messages);

                    return voxDetail;
                }
            };
}
