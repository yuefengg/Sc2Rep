var project = app.project;

function incrementClipCount(trackNumber) {
  if (trackClipMap.hasOwnProperty(trackNumber)) {
      var currentCount = trackClipMap[trackNumber];
      trackClipMap[trackNumber] = currentCount + 1;
  } else {
      alert("轨道 " + trackNumber + " 不存在于对象中。");
  }
}


function insertClipIntoTrack(sequence, binName, fileName, trackNumber, insertTimeStr) {
  var videoTracks = sequence.videoTracks;

  if (videoTracks.length >= trackNumber) {
      // 获取目标视频轨道（索引从0开始，所以需要减1）
      var targetTrack = videoTracks[trackNumber - 1];

      // 查找指定名称的素材箱
      var rootItem = app.project.rootItem;
      var targetBin = null;

      for (var i = 0; i < rootItem.children.numItems; i++) {
          if (rootItem.children[i].name === binName && rootItem.children[i].type === ProjectItemType.BIN) {
              targetBin = rootItem.children[i];
              break;
          }
      }

      if (targetBin) {
          // 在素材箱中查找指定文件
          var targetFile = null;

          for (var j = 0; j < targetBin.children.numItems; j++) {
              if (targetBin.children[j].name === fileName) {
                  targetFile = targetBin.children[j];
                  break;
              }
          }



          // 如果找不到指定文件，选择 default.mp4
          if (!targetFile) {
              for (var j = 0; j < targetBin.children.numItems; j++) {
                  if (targetBin.children[j].name === "default.mp4") {
                      targetFile = targetBin.children[j];
                      alert("未找到文件 " + fileName + "，选择默认文件 default.mp4");
                      break;
                  }
              }

              // 如果 default.mp4 也不存在，打印错误消息
              if (!targetFile) {
                  alert("未找到文件 " + fileName + " 和默认文件 default.mp4");
              }
          }

          if (targetFile) {
            // 获取文件的元数据
            var metadata = targetFile.getProjectMetadata();
            var width = metadata.match(/<Column.Property.Name>Frame Width<\/Column.Property.Name><Column.Property.Value>(\d+)<\/Column.Property.Value>/);
            var height = metadata.match(/<Column.Property.Name>Frame Height<\/Column.Property.Name><Column.Property.Value>(\d+)<\/Column.Property.Value>/);

            if (width && height) {
                alert("文件 " + fileName + " 的分辨率是 " + width[1] + "x" + height[1] + ".");
            }}



          if (targetFile) {
            // 将插入时间字符串 "12:23" 转换为秒数，并加上固定延长秒数
            var timeParts = insertTimeStr.split(":");
            var insertTime = parseInt(timeParts[0]) * 60 + parseInt(timeParts[1]) + TTime; // 70秒的延长时间
            // targetTrack.insertClip(targetFile, insertTime);
            targetTrack.overwriteClip(targetFile, insertTime);
            incrementClipCount(trackNumber);

            // 你可以在这里添加更多的代码来设置新剪辑的位置或其他属性
        }
      } else {
          alert("素材箱 " + binName + " 未找到。");
      }
  } else {
      alert("轨道 " + trackNumber + " 不存在。");
  }
}


function setClipPosition(sequence, trackIndex, clipIndex, posX, posY) {
  var Track = sequence.videoTracks[trackIndex - 1];

  var effects = Track.clips[clipIndex - 1].components;

  for (var i = 0; i < effects.length; i++) {
      var effect = effects[i];
      if (effect.displayName === "运动") {
          var props = effect.properties;
          for (var j = 0; j < props.length; j++) {
              var prop = props[j];
              if (prop.displayName === "位置") {
                  prop.setValue([posX / 1920, posY / 1080]);
              }
          }
      }
  }
}
function setPr(sequence,binName, fileName,trackIndex,insertMinutes, insertSeconds){


  // 调用函数，示例参数：素材箱名 "T1"，文件名 "1.mp4"，插入到第4轨道，插入时间为1分2秒
  insertClipIntoTrack(sequence,binName, fileName, trackIndex, insertMinutes, insertSeconds);
  // 调用函数，示例参数：当前sequence，第1个轨道，第1个剪辑，位置为(500, 500)

  setClipPosition(
    sequence,
    trackIndex,
    trackClipMap[trackIndex],
    trackPos[trackIndex][0],
    trackPos[trackIndex][1]
);

}

// 获取当前活动的sequence
var sequence = project.activeSequence;



// 使用普通对象来存储轨道和它的剪辑数
var trackClipMap = {};
var trackPos = {};

// 初始化轨道4到轨道8的剪辑数为0
for (var i = 1; i <= 10; i++) {
    trackClipMap[i] = 0;
}

// 初始化轨道的位置
trackPos[1] = [200, 200];
trackPos[2] = [300, 200];
trackPos[3] = [800, 200];
//自己农民死亡
trackPos[4] = [1816, 650];
//敌方农民死亡
trackPos[5] = [100, 650];
//科技1-4
trackPos[6] = [1812, 200];
trackPos[7] = [1812, 300];
trackPos[8] = [1812, 400];
trackPos[9] = [1812, 500];
//战损栏
trackPos[10] = [1692,905];

TTime = 61;
// sequence,素材箱,素材名字,轨道,分钟,秒速

// 4是我方农民
// 5是敌方农民
// 6科技1
// 7科技2
// 8科技3
// 9科技4

// setPr(sequence,"T1", "1.mp4",1,"1:21");
// setPr(sequence,"T2", "2.mp4",1,"2:23");
// setPr(sequence,"T2", "2.mp4",1,"3:25");
// setPr(sequence,"T2", "2.mp4",2,"2:22");






