package data.tasks;

import java.util.List;

/**
 * Created by hua on 2017/8/17.
 */
public class ChannelBean {

    private List<Result> resultList;

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> T1348647909107) {
        this.resultList = T1348647909107;
    }

    public static class Result {
        /**
         * adtype : 0
         * boardid : news2_bbs
         * clkNum : 0
         * danmu : 1
         * digest : 中国青年网北京8月16日电党的十八大以来，以习近平同志为核心的党中央统揽伟大斗争、伟大工程、伟大事业、伟大梦想，把全面从严治党纳入“四个全面”战略布局。这五年，
         * docid : CRVOF6E4000189FH
         * downTimes : 0
         * hasHead : 0
         * id : CRVOF6E4000189FH
         * img : http://cms-bucket.nosdn.127.net/f52a868345a24c0d95f4d4117d2ad81b20170816165533.png
         * imgType : 0
         * imgsrc : http://cms-bucket.nosdn.127.net/f52a868345a24c0d95f4d4117d2ad81b20170816165533.png
         * imgsum : 0
         * interest : S
         * lmodify : 2017-08-16 16:58:13
         * picCount : 0
         * program : LMA1
         * prompt : 成功为您推荐10条新内容
         * ptime : 2017-08-16 16:55:40
         * rank : 0
         * recNews : 0
         * recReason : 突发新闻
         * recSource : #
         * recType : 0
         * recprog : FILTER_ACT_ALL
         * refreshPrompt : 0
         * replyCount : 0
         * replyid : CRVOF6E4000189FH
         * source : 中国青年网
         * template : normal1
         * title : 党风正 民风清 习近平全面从严治党进行时
         * upTimes : 5
         * unlikeReason : ["社会/1","官员变动/2","党委书记/3","来源:中央纪委监察部网站/4","内容质量差/6"]
         * TAG : 本地:深圳
         * TAGS : 本地:深圳
         * imgnewextra : [{"imgsrc":"http://dmr.nosdn.127.net/v-20170815-2b1183abfaf5bfe2e0bd73237986d953.jpg"},{"imgsrc":"http://dmr.nosdn.127.net/v-20170815-7f213f8cf0897244ae45144ac621a3dd.jpg"}]
         * skipID : VJQCO87MO
         * skipType : video
         * videoID : VJQCO87MO
         * videoinfo : {"cover":"http://vimg2.ws.126.net/image/snapshot/2017/8/M/P/VJQCO87MP.jpg","danmu":1,"description":"","length":162,"m3u8_url":"http://flv.bn.netease.com/videolib3/1708/08/CHano1901/SD/movie_index.m3u8","mp4_url":"http://flv3.bn.netease.com/videolib3/1708/08/CHano1901/SD/CHano1901-mobile.mp4","playCount":0,"playersize":1,"ptime":"2017-08-15 13:42:03.0","replyBoard":"video_bbs","replyCount":0,"replyid":"JQCO87MO050835RB","sectiontitle":"http://vimg1.ws.126.net/image/snapshot/2017/2/U/4/VCCHQ9SU4.jpg","sizeHD":16200,"sizeSD":12150,"sizeSHD":24300,"title":"中印对峙这国坐山观虎斗？印度如此嚣张原来有流氓撑腰！","topicDesc":"云集优秀军事记者，汇聚一流军事专家。第一军情为您提供：及时的报道、权威的分析、深度的评论。\r\n著名军事记者贾永领衔打造独具特色文字、视频、图片报道。\r\n贾永，先后供职于新华社、央视，范长江新闻奖获得者。","topicImg":"http://vimg1.ws.126.net/image/snapshot/2017/2/U/4/VCCHQ9SU4.jpg","topicName":"第一军情","topicSid":"VCCHQ9STP","vid":"VJQCO87MO","videoTopic":{"alias":"及时报道、权威分析、深度评论","ename":"T1487242302354","tid":"T1487242302354","tname":"第一军情","topic_icons":"http://dingyue.nosdn.127.net/k=VDYvoUnGOCE=N12RP7enM2CsucilM=1tL8PyDSsCLtt1487242301931.jpg"},"videosource":"新媒体"}
         */

        private int adtype;
        private String boardid;
        private int clkNum;
        private int danmu;
        private String digest;
        private String docid;
        private int downTimes;
        private int hasHead;
        private String id;
        private String img;
        private int imgType;
        private String imgsrc;
        private int imgsum;
        private String interest;
        private String lmodify;
        private int picCount;
        private String program;
        private String prompt;
        private String ptime;
        private int rank;
        private int recNews;
        private String recReason;
        private String recSource;
        private int recType;
        private String recprog;
        private int refreshPrompt;
        private int replyCount;
        private String replyid;
        private String source;
        private String template;
        private String title;
        private int upTimes;
        private String TAG;
        private String TAGS;
        private String skipID;
        private String skipType;
        private String videoID;
        private VideoinfoBean videoinfo;
        private List<String> unlikeReason;
        private List<ImgnewextraBean> imgnewextra;

        public int getAdtype() {
            return adtype;
        }

        public void setAdtype(int adtype) {
            this.adtype = adtype;
        }

        public String getBoardid() {
            return boardid;
        }

        public void setBoardid(String boardid) {
            this.boardid = boardid;
        }

        public int getClkNum() {
            return clkNum;
        }

        public void setClkNum(int clkNum) {
            this.clkNum = clkNum;
        }

        public int getDanmu() {
            return danmu;
        }

        public void setDanmu(int danmu) {
            this.danmu = danmu;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public int getDownTimes() {
            return downTimes;
        }

        public void setDownTimes(int downTimes) {
            this.downTimes = downTimes;
        }

        public int getHasHead() {
            return hasHead;
        }

        public void setHasHead(int hasHead) {
            this.hasHead = hasHead;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getImgType() {
            return imgType;
        }

        public void setImgType(int imgType) {
            this.imgType = imgType;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public int getImgsum() {
            return imgsum;
        }

        public void setImgsum(int imgsum) {
            this.imgsum = imgsum;
        }

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        public String getLmodify() {
            return lmodify;
        }

        public void setLmodify(String lmodify) {
            this.lmodify = lmodify;
        }

        public int getPicCount() {
            return picCount;
        }

        public void setPicCount(int picCount) {
            this.picCount = picCount;
        }

        public String getProgram() {
            return program;
        }

        public void setProgram(String program) {
            this.program = program;
        }

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public int getRecNews() {
            return recNews;
        }

        public void setRecNews(int recNews) {
            this.recNews = recNews;
        }

        public String getRecReason() {
            return recReason;
        }

        public void setRecReason(String recReason) {
            this.recReason = recReason;
        }

        public String getRecSource() {
            return recSource;
        }

        public void setRecSource(String recSource) {
            this.recSource = recSource;
        }

        public int getRecType() {
            return recType;
        }

        public void setRecType(int recType) {
            this.recType = recType;
        }

        public String getRecprog() {
            return recprog;
        }

        public void setRecprog(String recprog) {
            this.recprog = recprog;
        }

        public int getRefreshPrompt() {
            return refreshPrompt;
        }

        public void setRefreshPrompt(int refreshPrompt) {
            this.refreshPrompt = refreshPrompt;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public String getReplyid() {
            return replyid;
        }

        public void setReplyid(String replyid) {
            this.replyid = replyid;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUpTimes() {
            return upTimes;
        }

        public void setUpTimes(int upTimes) {
            this.upTimes = upTimes;
        }

        public String getTAG() {
            return TAG;
        }

        public void setTAG(String TAG) {
            this.TAG = TAG;
        }

        public String getTAGS() {
            return TAGS;
        }

        public void setTAGS(String TAGS) {
            this.TAGS = TAGS;
        }

        public String getSkipID() {
            return skipID;
        }

        public void setSkipID(String skipID) {
            this.skipID = skipID;
        }

        public String getSkipType() {
            return skipType;
        }

        public void setSkipType(String skipType) {
            this.skipType = skipType;
        }

        public String getVideoID() {
            return videoID;
        }

        public void setVideoID(String videoID) {
            this.videoID = videoID;
        }

        public VideoinfoBean getVideoinfo() {
            return videoinfo;
        }

        public void setVideoinfo(VideoinfoBean videoinfo) {
            this.videoinfo = videoinfo;
        }

        public List<String> getUnlikeReason() {
            return unlikeReason;
        }

        public void setUnlikeReason(List<String> unlikeReason) {
            this.unlikeReason = unlikeReason;
        }

        public List<ImgnewextraBean> getImgnewextra() {
            return imgnewextra;
        }

        public void setImgnewextra(List<ImgnewextraBean> imgnewextra) {
            this.imgnewextra = imgnewextra;
        }

        public static class VideoinfoBean {
            /**
             * cover : http://vimg2.ws.126.net/image/snapshot/2017/8/M/P/VJQCO87MP.jpg
             * danmu : 1
             * description :
             * length : 162
             * m3u8_url : http://flv.bn.netease.com/videolib3/1708/08/CHano1901/SD/movie_index.m3u8
             * mp4_url : http://flv3.bn.netease.com/videolib3/1708/08/CHano1901/SD/CHano1901-mobile.mp4
             * playCount : 0
             * playersize : 1
             * ptime : 2017-08-15 13:42:03.0
             * replyBoard : video_bbs
             * replyCount : 0
             * replyid : JQCO87MO050835RB
             * sectiontitle : http://vimg1.ws.126.net/image/snapshot/2017/2/U/4/VCCHQ9SU4.jpg
             * sizeHD : 16200
             * sizeSD : 12150
             * sizeSHD : 24300
             * title : 中印对峙这国坐山观虎斗？印度如此嚣张原来有流氓撑腰！
             * topicDesc : 云集优秀军事记者，汇聚一流军事专家。第一军情为您提供：及时的报道、权威的分析、深度的评论。
             著名军事记者贾永领衔打造独具特色文字、视频、图片报道。
             贾永，先后供职于新华社、央视，范长江新闻奖获得者。
             * topicImg : http://vimg1.ws.126.net/image/snapshot/2017/2/U/4/VCCHQ9SU4.jpg
             * topicName : 第一军情
             * topicSid : VCCHQ9STP
             * vid : VJQCO87MO
             * videoTopic : {"alias":"及时报道、权威分析、深度评论","ename":"T1487242302354","tid":"T1487242302354","tname":"第一军情","topic_icons":"http://dingyue.nosdn.127.net/k=VDYvoUnGOCE=N12RP7enM2CsucilM=1tL8PyDSsCLtt1487242301931.jpg"}
             * videosource : 新媒体
             */

            private String cover;
            private int danmu;
            private String description;
            private int length;
            private String m3u8_url;
            private String mp4_url;
            private int playCount;
            private int playersize;
            private String ptime;
            private String replyBoard;
            private int replyCount;
            private String replyid;
            private String sectiontitle;
            private int sizeHD;
            private int sizeSD;
            private int sizeSHD;
            private String title;
            private String topicDesc;
            private String topicImg;
            private String topicName;
            private String topicSid;
            private String vid;
            private VideoTopicBean videoTopic;
            private String videosource;

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public int getDanmu() {
                return danmu;
            }

            public void setDanmu(int danmu) {
                this.danmu = danmu;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getLength() {
                return length;
            }

            public void setLength(int length) {
                this.length = length;
            }

            public String getM3u8_url() {
                return m3u8_url;
            }

            public void setM3u8_url(String m3u8_url) {
                this.m3u8_url = m3u8_url;
            }

            public String getMp4_url() {
                return mp4_url;
            }

            public void setMp4_url(String mp4_url) {
                this.mp4_url = mp4_url;
            }

            public int getPlayCount() {
                return playCount;
            }

            public void setPlayCount(int playCount) {
                this.playCount = playCount;
            }

            public int getPlayersize() {
                return playersize;
            }

            public void setPlayersize(int playersize) {
                this.playersize = playersize;
            }

            public String getPtime() {
                return ptime;
            }

            public void setPtime(String ptime) {
                this.ptime = ptime;
            }

            public String getReplyBoard() {
                return replyBoard;
            }

            public void setReplyBoard(String replyBoard) {
                this.replyBoard = replyBoard;
            }

            public int getReplyCount() {
                return replyCount;
            }

            public void setReplyCount(int replyCount) {
                this.replyCount = replyCount;
            }

            public String getReplyid() {
                return replyid;
            }

            public void setReplyid(String replyid) {
                this.replyid = replyid;
            }

            public String getSectiontitle() {
                return sectiontitle;
            }

            public void setSectiontitle(String sectiontitle) {
                this.sectiontitle = sectiontitle;
            }

            public int getSizeHD() {
                return sizeHD;
            }

            public void setSizeHD(int sizeHD) {
                this.sizeHD = sizeHD;
            }

            public int getSizeSD() {
                return sizeSD;
            }

            public void setSizeSD(int sizeSD) {
                this.sizeSD = sizeSD;
            }

            public int getSizeSHD() {
                return sizeSHD;
            }

            public void setSizeSHD(int sizeSHD) {
                this.sizeSHD = sizeSHD;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTopicDesc() {
                return topicDesc;
            }

            public void setTopicDesc(String topicDesc) {
                this.topicDesc = topicDesc;
            }

            public String getTopicImg() {
                return topicImg;
            }

            public void setTopicImg(String topicImg) {
                this.topicImg = topicImg;
            }

            public String getTopicName() {
                return topicName;
            }

            public void setTopicName(String topicName) {
                this.topicName = topicName;
            }

            public String getTopicSid() {
                return topicSid;
            }

            public void setTopicSid(String topicSid) {
                this.topicSid = topicSid;
            }

            public String getVid() {
                return vid;
            }

            public void setVid(String vid) {
                this.vid = vid;
            }

            public VideoTopicBean getVideoTopic() {
                return videoTopic;
            }

            public void setVideoTopic(VideoTopicBean videoTopic) {
                this.videoTopic = videoTopic;
            }

            public String getVideosource() {
                return videosource;
            }

            public void setVideosource(String videosource) {
                this.videosource = videosource;
            }

            public static class VideoTopicBean {
                /**
                 * alias : 及时报道、权威分析、深度评论
                 * ename : T1487242302354
                 * tid : T1487242302354
                 * tname : 第一军情
                 * topic_icons : http://dingyue.nosdn.127.net/k=VDYvoUnGOCE=N12RP7enM2CsucilM=1tL8PyDSsCLtt1487242301931.jpg
                 */

                private String alias;
                private String ename;
                private String tid;
                private String tname;
                private String topic_icons;

                public String getAlias() {
                    return alias;
                }

                public void setAlias(String alias) {
                    this.alias = alias;
                }

                public String getEname() {
                    return ename;
                }

                public void setEname(String ename) {
                    this.ename = ename;
                }

                public String getTid() {
                    return tid;
                }

                public void setTid(String tid) {
                    this.tid = tid;
                }

                public String getTname() {
                    return tname;
                }

                public void setTname(String tname) {
                    this.tname = tname;
                }

                public String getTopic_icons() {
                    return topic_icons;
                }

                public void setTopic_icons(String topic_icons) {
                    this.topic_icons = topic_icons;
                }
            }
        }

        public static class ImgnewextraBean {
            /**
             * imgsrc : http://dmr.nosdn.127.net/v-20170815-2b1183abfaf5bfe2e0bd73237986d953.jpg
             */

            private String imgsrc;

            public String getImgsrc() {
                return imgsrc;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }
        }
    }
}
