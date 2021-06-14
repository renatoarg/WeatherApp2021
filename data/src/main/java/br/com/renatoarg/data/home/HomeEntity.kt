package br.com.renatoarg.data.home

data class Welcome4 (
    val data: Data,
    val warnings: List<Warning>
)

data class Data (
    val timelines: List<Timeline>
)

data class Timeline (
    val timestep: String,
    val startTime: String,
    val endTime: String,
    val intervals: List<Interval>
)

data class Interval (
    val startTime: String,
    val values: Map<String, Double?>
)

data class Warning (
    val code: Long,
    val type: String,
    val message: String,
    val meta: Meta
)

data class Meta (
    val timestep: String,
    val from: String,
    val to: String
)
