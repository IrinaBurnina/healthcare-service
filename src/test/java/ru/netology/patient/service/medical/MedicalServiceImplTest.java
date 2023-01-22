package ru.netology.patient.service.medical;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.service.alert.SendAlertServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

public class MedicalServiceImplTest {
    @ParameterizedTest
    @MethodSource("sourceMethodForCheckBloodPressureTestNoneSend")
    public void checkBloodPressureTestNoneSend(PatientInfo patientInfo, BloodPressure bloodPressure) {
        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());
        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoFileRepository.getById(patientInfo.getId()))
                .thenReturn(patientInfo);
        SendAlertServiceImpl alertService = Mockito.mock(SendAlertServiceImpl.class);
        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoFileRepository, alertService);
        medicalService.checkBloodPressure(patientInfo.getId(), bloodPressure);
        Mockito.verify(alertService, Mockito.times(0)).send(message);
    }

    public static Stream<Arguments> sourceMethodForCheckBloodPressureTestNoneSend() {
        return Stream.of(Arguments.of(new PatientInfo("Иван",
                        "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal(36.65), new BloodPressure(120, 80))),
                new BloodPressure(120, 80)), Arguments.of(new PatientInfo("Семен", "Михайлов",
                LocalDate.of(1982, 01, 16), new HealthInfo(new BigDecimal(36.6),
                new BloodPressure(125, 78))), new BloodPressure(125, 78)));
    }

    @ParameterizedTest
    @MethodSource("sourceMethodForCheckBloodPressureTestSend")
    public void checkBloodPressureTestSend(PatientInfo patientInfo, BloodPressure bloodPressure) {
        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoFileRepository.getById(patientInfo.getId()))
                .thenReturn(patientInfo);
        SendAlertServiceImpl alertService = Mockito.mock(SendAlertServiceImpl.class);
        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoFileRepository, alertService);
        medicalService.checkBloodPressure(patientInfo.getId(), bloodPressure);
        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());
        Mockito.verify(alertService, Mockito.times(1)).send(message);
    }

    public static Stream<Arguments> sourceMethodForCheckBloodPressureTestSend() {
        return Stream.of(Arguments.of(new PatientInfo("Иван",
                        "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal(36.65), new BloodPressure(140, 80))),
                new BloodPressure(120, 80)), Arguments.of(new PatientInfo("Семен", "Михайлов",
                LocalDate.of(1982, 01, 16), new HealthInfo(new BigDecimal(36.6),
                new BloodPressure(125, 42))), new BloodPressure(100, 50)));
    }
    @ParameterizedTest
    @MethodSource("sourceMethodForCheckBloodPressureTestSend")
    public void checkBloodPressureTestSendValue(PatientInfo patientInfo, BloodPressure bloodPressure) {
        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoFileRepository.getById(patientInfo.getId()))
                .thenReturn(patientInfo);
        SendAlertServiceImpl alertService = Mockito.mock(SendAlertServiceImpl.class);
        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoFileRepository, alertService);
        medicalService.checkBloodPressure(patientInfo.getId(), bloodPressure);
        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());
        ArgumentCaptor<String> argumentCaptor=ArgumentCaptor.forClass(String.class);
        Mockito.verify(alertService).send(argumentCaptor.capture());
        Assertions.assertEquals(message,argumentCaptor.getValue());
        }
    @ParameterizedTest
    @MethodSource("sourceMethodForCheckNormalTemperatureTestNoneSend")
    public void checkNormalTemperatureTestNoneSend(PatientInfo patientInfo, BigDecimal temperature) {
        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());
        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoFileRepository.getById(patientInfo.getId()))
                .thenReturn(patientInfo);
        SendAlertServiceImpl alertService = Mockito.mock(SendAlertServiceImpl.class);
        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoFileRepository, alertService);
        medicalService.checkTemperature(patientInfo.getId(), temperature);
        Mockito.verify(alertService, Mockito.times(0)).send(message);
    }

    public static Stream<Arguments> sourceMethodForCheckNormalTemperatureTestNoneSend() {
        return Stream.of(Arguments.of(new PatientInfo("Иван",
                        "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal(36.65), new BloodPressure(120, 80))),
                new BigDecimal(36.65)), Arguments.of(new PatientInfo("Семен", "Михайлов",
                LocalDate.of(1982, 01, 16), new HealthInfo(new BigDecimal(36.6),
                new BloodPressure(125, 78))), new BigDecimal(36.6)));
    }

    @ParameterizedTest
    @MethodSource("sourceMethodForCheckNormalTemperatureTestSend")
    public void checkNormalTemperatureTestSend(PatientInfo patientInfo, BigDecimal temperature) {
        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());
        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoFileRepository.getById(patientInfo.getId()))
                .thenReturn(patientInfo);
        SendAlertServiceImpl alertService = Mockito.mock(SendAlertServiceImpl.class);
        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoFileRepository, alertService);
        medicalService.checkTemperature(patientInfo.getId(), temperature);
        Mockito.verify(alertService, Mockito.times(1)).send(message);
    }

    public static Stream<Arguments> sourceMethodForCheckNormalTemperatureTestSend() {
        return Stream.of(Arguments.of(new PatientInfo("Иван",
                        "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal(36.65), new BloodPressure(120, 80))),
                new BigDecimal(35.00)), Arguments.of(new PatientInfo("Семен", "Михайлов",
                LocalDate.of(1982, 01, 16), new HealthInfo(new BigDecimal(36.6),
                new BloodPressure(125, 78))), new BigDecimal(34.0)));
    }

}
