package com.angema.hours.app.feature.record;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    private List<Record> getAll() {
        return recordService.getAllRecord();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    private Record getId(@PathVariable("id") Long id) {
        return recordService.getIdRecord(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/filter")
    private List<Record> getFilter(@RequestParam (required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate datefrom,
                                   @RequestParam (required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateto,
                                   @RequestParam (required = false) Long operator,
                                   @RequestParam (required = false) Long project) {
        return recordService.getListFilter(datefrom, dateto, operator, project);
    }

    //@ResponseBody
    //@ResponseStatus(HttpStatus.OK)
    //@GetMapping("/statistics")
    //private List<RecordStatistics> getStatistics () {
    //    return recordService.getStatistics();
    //}

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    private Record save(@Valid @RequestBody Record data, BindingResult bindingResult) {
        return recordService.saveRecord(data, bindingResult);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    private Record delete(@PathVariable("id") Long id) {
        return recordService.deleteRecord(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    private Record update(@PathVariable("id") Long id, @Valid @RequestBody Record data, BindingResult bindingResult) {
        return recordService.updateRecord(data, id, bindingResult);
    }
}
