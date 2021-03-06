import Styles from "./remote.module.css";
import Clap from "./audio/Clap.wav";
import Tambourine from "./audio/Tambourine.mp3";

function Effect({
  setOpenFirework,
  hideControl,
  sendClap,
  sendTambourine,
  sendFire,
  voiceFilterEcho,
  voiceFilterMegaPhone,
  voiceFilterModulation,
}) {
  return (
    <div>
      {/* 템포 업 */}
      <button className={Styles.jump} style={{ left: "1.2vw", top: "1.2vh" }}>
        간주점프
      </button>

      {/* 템포 다운*/}
      {/* <button className={Styles.tempo} 
                style={{left: '32px', top: '50px'}}>
                    ????&nbsp;&nbsp;
                    <div className={Styles.tempodownbtn} ></div>
            </button> */}

      {/* 에코*/}
      <button className={Styles.echo} onClick={voiceFilterEcho}>
        에코
      </button>

      {/* 음성변조*/}
      <button className={Styles.change} onClick={voiceFilterModulation}>
        음성 변조
      </button>

      {/* 조명 업 */}
      <button
        className={Styles.light}
        onClick={voiceFilterMegaPhone}
        style={{ left: "14.8vw", top: "1.2vh" }}
      >
        음향 &nbsp;
        <div className={Styles.lightup}></div>
      </button>

      {/* 조명 다운*/}
      <button className={Styles.light} style={{ left: "14.8vw", top: "5vh" }}>
        조명 &nbsp;
        <div className={Styles.lightdown}></div>
      </button>

      {/* 필터*/}
      <button className={Styles.fgbtn} style={{ left: "20.6vw", top: "1.2vh" }}>
        필터
      </button>
      {/* 배경*/}
      <button className={Styles.fgbtn} style={{ left: "20.6vw", top: "5vh" }}>
        배경
      </button>
      {/* 박수 */}
      <button
        className={Styles.effectbtn}
        onClick={() => {
          sendClap();
          hideControl();
        }}
        style={{ left: "27.5vw", top: "1.8vh" }}
      >
        <img
          src="https://cdn-icons-png.flaticon.com/512/2446/2446723.png"
          style={{ width: "100%" }}
          alt=""
        />
      </button>
      {/* 탬버린 */}
      <button
        className={Styles.effectbtn}
        onClick={() => {
          sendTambourine();
          hideControl();
        }}
        style={{ left: "32vw", top: "1.8vh" }}
      >
        <img
          src="https://cdn-icons-png.flaticon.com/512/1426/1426588.png"
          style={{ width: "100%" }}
          alt=""
        />
      </button>
      {/* 폭죽 */}
      <button
        className={Styles.effectbtn}
        onClick={() => {
          sendFire();
          hideControl();
        }}
        style={{ left: "36.5vw", top: "1.8vh" }}
      >
        <img
          src="https://cdn-icons-png.flaticon.com/512/1700/1700807.png"
          style={{ width: "100%" }}
          alt=""
        />
      </button>
      {/* 좋아요 */}
      {/* <button className={Styles.effectbtn} style={{left: '790px', top: '15px'}}> */}

      {/* </button> */}
    </div>
  );
}

export default Effect;
