#include "../../../include/work_tinax_midij_play_RtMidiDevice.h"
#include "../../../include/work_tinax_midij_play_RtMidiInitializer.h"
#include <RtMidi.h>
#include <string>
#include <vector>

#ifdef __cplusplus
extern "C" {
#endif
static void throw_RtMidiException_with_msg(JNIEnv *env, const std::string& msg) {
  jclass rtexCls = env->FindClass("work/tinax/midij/play/RtMidiException");
  env->ThrowNew(rtexCls, msg.c_str());
}

static RtMidiOut *get_rtMidi_out(JNIEnv *env, jobject self) {
  jfieldID rtMidiOutF = env->GetFieldID(env->GetObjectClass(self), "rtMidiOut", "J");
  jlong rtMidiOutL = env->GetLongField(self, rtMidiOutF);
  RtMidiOut *rtMidiOut = reinterpret_cast<RtMidiOut *>(rtMidiOutL);
  if (rtMidiOut == nullptr) {
    throw_RtMidiException_with_msg(env, "RtMidiOut is not initialized");
    return nullptr;
  } else {
    return rtMidiOut;
  }
}

/*
 * Class:     work_tinax_midij_play_RtMidiDevice
 * Method:    setRtMidiOut
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_work_tinax_midij_play_RtMidiDevice_setRtMidiOut
  (JNIEnv *env, jobject self) {
  try {
    RtMidiOut *rtMidiOut = new RtMidiOut();
    jfieldID rtMidiOutF = env->GetFieldID(env->GetObjectClass(self), "rtMidiOut", "J");
    env->SetLongField(self, rtMidiOutF, reinterpret_cast<jlong>(rtMidiOut));
  } catch (const std::exception& ex) {
    const std::string& msg = ex.what();
    throw_RtMidiException_with_msg(env, msg.c_str());
  }
}

/*
 * Class:     work_tinax_midij_play_RtMidiDevice
 * Method:    destroyRtMidiOut
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_work_tinax_midij_play_RtMidiDevice_destroyRtMidiOut
  (JNIEnv *env, jobject self) {
  jfieldID rtMidiOutF = env->GetFieldID(env->GetObjectClass(self), "rtMidiOut", "J");
  jlong rtMidiOutL = env->GetLongField(self, rtMidiOutF);
  if (rtMidiOutL == 0) return;
  RtMidiOut *rtMidiOut = reinterpret_cast<RtMidiOut *>(rtMidiOutL);
  delete rtMidiOut; // TODO: close first?
  env->SetLongField(self, rtMidiOutF, 0);
}

/*
 * Class:     work_tinax_midij_play_RtMidiDevice
 * Method:    getDeviceNames
 * Signature: ()[Ljava/lang/String;
 */
JNIEXPORT jobjectArray JNICALL Java_work_tinax_midij_play_RtMidiDevice_getDeviceNames
  (JNIEnv *env, jobject self) {
  auto *rtMidiOut = get_rtMidi_out(env, self);
  const auto portCount = rtMidiOut->getPortCount();
  std::vector<std::string> devices(portCount);
  for (int i=0; i<portCount; i++) {
    devices.at(i) = rtMidiOut->getPortName(i);
  }

  jclass strCls = env->FindClass("java/lang/String");
  jobjectArray result = env->NewObjectArray(devices.size(), strCls, nullptr);
  for (size_t i=0; i<devices.size(); i++) {
    env->SetObjectArrayElement(result, i, env->NewStringUTF(devices.at(i).c_str()));
  }
  return result;
}

/*
 * Class:     work_tinax_midij_play_RtMidiDevice
 * Method:    openDevice
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_work_tinax_midij_play_RtMidiDevice_openDevice__Ljava_lang_String_2
  (JNIEnv *env, jobject self, jstring device_name) {
  RtMidiOut *rtMidiOut = get_rtMidi_out(env, self);

}

/*
 * Class:     work_tinax_midij_play_RtMidiDevice
 * Method:    openDevice
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_work_tinax_midij_play_RtMidiDevice_openDevice__I
  (JNIEnv *env, jobject self, jint device_name) {
  RtMidiOut *rtMidiOut = get_rtMidi_out(env, self);
  try {
    rtMidiOut->openPort(static_cast<unsigned int>(device_name));
  } catch (const std::exception& ex) {
    throw_RtMidiException_with_msg(env, ex.what());
  }
}

/*
 * Class:     work_tinax_midij_play_RtMidiDevice
 * Method:    closeDevice
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_work_tinax_midij_play_RtMidiDevice_closeDevice
  (JNIEnv *env, jobject self) {
  RtMidiOut *rtMidiOut = get_rtMidi_out(env, self);
  rtMidiOut->closePort();
}

/*
 * Class:     work_tinax_midij_play_RtMidiDevice
 * Method:    getCurrentPortName
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_work_tinax_midij_play_RtMidiDevice_getCurrentPortName
  (JNIEnv *env, jobject self) {
  RtMidiOut *rtMidiOut = get_rtMidi_out(env, self);
  if (!rtMidiOut->isPortOpen()) {
    return nullptr;
  }
  return nullptr; // TODO
}

/*
 * Class:     work_tinax_midij_play_RtMidiDevice
 * Method:    getRtMidiApiName
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_work_tinax_midij_play_RtMidiDevice_getRtMidiApiName
  (JNIEnv *env, jobject self) {
  RtMidiOut *rtMidiOut = get_rtMidi_out(env, self);
  const auto api = rtMidiOut->getCurrentApi();
  std::string apiString = "<error>";
  switch (api) {
  case RtMidi::Api::MACOSX_CORE:
    apiString = "MACOSX_CORE";
    break;
  case RtMidi::Api::LINUX_ALSA:
    apiString = "LINUX_ALSA";
    break;
  case RtMidi::Api::UNIX_JACK:
    apiString = "UNIX_JACK";
    break;
  case RtMidi::Api::WINDOWS_MM:
    apiString = "WINDOWS_MM";
    break;
  case RtMidi::Api::RTMIDI_DUMMY:
    apiString = "RTMIDI_DUMMY";
    break;
  }
  return env->NewStringUTF(apiString.c_str());
}

/*
 * Class:     work_tinax_midij_play_RtMidiDevice
 * Method:    sendMessage
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Java_work_tinax_midij_play_RtMidiDevice_sendMessage
  (JNIEnv *env, jobject self, jbyteArray data) {
  RtMidiOut *rtMidiOut = get_rtMidi_out(env, self);
  size_t messages_len = env->GetArrayLength(data);
  jboolean jfalse = JNI_FALSE;
  jbyte *messages_raw = env->GetByteArrayElements(data, &jfalse);
  std::vector<unsigned char> messages(messages_len);
  for (size_t i=0; i<messages_len; i++) {
    messages[i] = static_cast<unsigned char>(messages_raw[i]);
  }
  try {
    rtMidiOut->sendMessage(&messages);
  } catch (const std::exception &ex) {
    env->ReleaseByteArrayElements(data, messages_raw, JNI_ABORT);
    throw_RtMidiException_with_msg(env, ex.what());
  }
}

#ifdef __cplusplus
}
#endif
